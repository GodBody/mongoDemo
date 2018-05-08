package com.springboot.analytics.controller;

import com.springboot.analytics.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class HomeController {

    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MongoTemplate mongo;

    public static <T> HttpEntity<T> setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Origin", "https://developer.riotgames.com");
        headers.set("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.set("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML,like Gecko) Chrome/64.0.3282.186 Safari/537.36");
        headers.set("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.set("X-Riot-Token", "RGAPI-448fde72-6811-467e-a194-01ba9a7e0a8d");
        return new HttpEntity<T>(headers);

    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public void index() {
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "summonerName") String name, Model model) throws Exception {

        System.out.println();
        System.out.println("Retrieved summoner : " + name);

        insert(name);

        String searchByName = "https://kr.api.riotgames.com/lol/summoner/v3/summoners/by-name/";

        HttpEntity<SummonerDTO> requestEn = setHeaders();
        ResponseEntity<SummonerDTO> responseEn = restTemplate.exchange(searchByName + name, HttpMethod.GET, requestEn,
                SummonerDTO.class);


        long summonerId = responseEn.getBody().getId();

        SummonerDTO summoner = mongo.findOne(Query.query(Criteria.where("_id").is(summonerId)), SummonerDTO.class, "Summoner");


        List<MatchDTO> matchList = new ArrayList<MatchDTO>();
        System.out.println("Number of games retrieved : " + summoner.getMatches().size());
        for (int i = 0; i < summoner.getMatches().size(); i++) {
            long gameId = summoner.getMatches().get(i).getGameId();
            MatchDTO match = mongo.findOne(Query.query(Criteria.where("_id").is(gameId)), MatchDTO.class, "Match");
            for (int j = 0; j < match.getParticipants().size(); j++) {
                ParticipantDTO p = match.getParticipants().get(j);


                p.getStats().setKdaRatio();


                int id = p.getChampionId();

                int id1 = p.getSpell1Id();
                int id2 = p.getSpell2Id();

                ChampionDTO temp = mongo.findOne(Query.query(Criteria.where("_id").is(id)), ChampionDTO.class, "Champion");

                p.setChampionName(temp.getImage().getFull());

                if (!match.isComplete()) {
                    if (p.getStats().isWin()) {
                        mongo.updateFirst(Query.query(Criteria.where("_id").is(p.getChampionId())), new Update().inc("winCount", 1), "Champion");
                    } else {
                        mongo.updateFirst(Query.query(Criteria.where("_id").is(p.getChampionId())), new Update().inc("lossCount", 1), "Champion");
                    }
                }
                SummonerSpellDTO spell1 = mongo.findOne(Query.query(Criteria.where("_id").is(id1)), SummonerSpellDTO.class, "SummonerSpell");
                SummonerSpellDTO spell2 = mongo.findOne(Query.query(Criteria.where("_id").is(id2)), SummonerSpellDTO.class, "SummonerSpell");
                if (spell1 == null || spell2 == null) {
                } else {
                    p.setSpell1Name(spell1.getImage().getFull());
                    p.setSpell2Name(spell2.getImage().getFull());
                }
            }
            mongo.updateFirst(Query.query(Criteria.where("_id").is(gameId)), new Update().set("complete", true), "Match");

            matchList.add(match);
        }
        for (int i = 0; i < matchList.size(); i++) {
            MatchDTO matchDTO = matchList.get(i);
            int totalKill = 0;
            int totalDeath = 0;
            int totalAssist = 0;
            int totalDamageDealtToChampions = 0;
            int totalDamageTaken = 0;
            for (int j = 0; j < matchDTO.getParticipants().size(); j++) {
                ParticipantDTO p = matchDTO.getParticipants().get(j);


                if (j <= 4) {
                    totalKill += p.getStats().getKills();
                    totalDeath += p.getStats().getDeaths();
                    totalAssist += p.getStats().getAssists();
                    totalDamageDealtToChampions += p.getStats().getTotalDamageDealtToChampions();
                    totalDamageTaken += p.getStats().getTotalDamageTaken();

                    matchDTO.getTeams().get(0).setTotalKills(totalKill);
                    matchDTO.getTeams().get(0).setTotalDeaths(totalDeath);
                    matchDTO.getTeams().get(0).setTotalAssist(totalAssist);
                    matchDTO.getTeams().get(0).setTotalDamageDealtToChampions(totalDamageDealtToChampions);
                    matchDTO.getTeams().get(0).setTotalDamageTaken(totalDamageTaken);

                    if (j == 4) {
                        totalKill = 0;
                        totalDeath = 0;
                        totalAssist = 0;
                        totalDamageDealtToChampions = 0;
                        totalDamageTaken = 0;
                    }
                } else {
                    totalKill += p.getStats().getKills();
                    totalDeath += p.getStats().getDeaths();
                    totalAssist += p.getStats().getAssists();
                    totalDamageDealtToChampions += p.getStats().getTotalDamageDealtToChampions();
                    totalDamageTaken += p.getStats().getTotalDamageTaken();


                    matchDTO.getTeams().get(1).setTotalKills(totalKill);
                    matchDTO.getTeams().get(1).setTotalDeaths(totalDeath);
                    matchDTO.getTeams().get(1).setTotalAssist(totalAssist);
                    matchDTO.getTeams().get(1).setTotalDamageDealtToChampions(totalDamageDealtToChampions);
                    matchDTO.getTeams().get(0).setTotalDamageTaken(totalDamageTaken);
                }
            }
        }


        List<MatchDTO> tempList = new ArrayList<>();
        System.out.println("Number of games registered : " + matchList.size());
        for (int i = 0; i < matchList.size(); i++) {
            MatchDTO match = matchList.get(i);
            for (int j = 0; j < match.getParticipantIdentities().size(); j++) {
                if (match.getParticipantIdentities().get(j).getPlayer().getSummonerId() == summonerId) {
                    MatchDTO tempDTO = new MatchDTO();
                    tempDTO.setParticipants(new ArrayList<>());

                    tempDTO.getParticipants().add(matchList.get(i).getParticipants().get(j));
                    tempList.add(tempDTO);
                }
            }

        }


        Map<String, MostChampion> map = new HashMap<>();
        List<MostChampion> list = new ArrayList<>();

        for (int i = 0; i < tempList.size(); i++) {
            for (int j = 0; j < tempList.get(i).getParticipants().size(); j++) {

                String cName = tempList.get(i).getParticipants().get(j).getChampionName();

                boolean isWin = tempList.get(i).getParticipants().get(j).getStats().isWin();
                int kill = tempList.get(i).getParticipants().get(j).getStats().getKills();
                int death = tempList.get(i).getParticipants().get(j).getStats().getDeaths();
                int assist = tempList.get(i).getParticipants().get(j).getStats().getAssists();

                MostChampion mostChampion = new MostChampion();

                if (map.containsKey(cName)) {
                    MostChampion get = map.get(cName);
                    get.addGameCount();
                    if (isWin)
                        get.addWinCount();
                    get.setLooseCount();

                    get.addKill(kill);
                    get.addDeath(death);
                    get.addAssist(assist);

                    map.put(cName, get);


                } else {
                    mostChampion.setChampionName(cName);

                    mostChampion.setGameCount(1);
                    if (isWin)
                        mostChampion.setWinCount(1);
                    mostChampion.setLooseCount();
                    mostChampion.setKill(kill);
                    mostChampion.setDeath(death);
                    mostChampion.setAssist(assist);

                    map.put(cName, mostChampion);


                }


            }
        }


        // map에서 get하여 kda, winrate set -> list.add
        Iterator<String> itr = map.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            map.get(key).setKdaRatio();
            map.get(key).setWinRate();
            list.add(map.get(key));

        }


        // list sort
        Collections.sort(list, new Comparator<MostChampion>() {
            @Override
            public int compare(MostChampion o1, MostChampion o2) {

                if (o1.getGameCount() > o2.getGameCount())
                    return -1;
                else if (o1.getGameCount() < o2.getGameCount())
                    return 1;
                else {
                    if (o1.getWinRate() > o2.getWinRate())
                        return -1;
                    else if (o1.getWinRate() < o2.getWinRate())
                        return 1;
                    else {
                        if (o1.getKdaRatio() > o2.getKdaRatio())
                            return -1;
                        else
                            return 1;
                    }

                }
            }
        });


        List<ChampionDTO> championList = mongo.findAll(ChampionDTO.class, "Champion");

        Collections.sort(championList, new Comparator<ChampionDTO>() {
            @Override
            public int compare(ChampionDTO o1, ChampionDTO o2) {
                if (o1.getWinCount() + o1.getLossCount() > o2.getWinCount() + o2.getLossCount())
                    return -1;
                else if (o1.getWinCount() + o1.getLossCount() == o2.getWinCount() + o2.getLossCount()) {
                    if (o1.getWinCount() > o2.getWinCount())
                        return -1;
                    else
                        return 1;
                } else
                    return 1;

            }
        });


        model.addAttribute("summoner", summoner);
        model.addAttribute("summonerName", name);
        model.addAttribute("matchList", matchList);
        model.addAttribute("championList", championList);
        model.addAttribute("mostList", list);

        return "search";
    }


    public void insert(String name) throws HttpClientErrorException {

        HttpHeaders headers = null;


        try {
            // SEARCH By Name
            String searchByName = "https://kr.api.riotgames.com/lol/summoner/v3/summoners/by-name/";

            HttpEntity<SummonerDTO> requestEn = setHeaders();
            ResponseEntity<SummonerDTO> responseEn = restTemplate.exchange(searchByName + name, HttpMethod.GET, requestEn,
                    SummonerDTO.class);

            headers = responseEn.getHeaders();

            SummonerDTO summonerDTO = responseEn.getBody();

            SummonerDTO summonerDB = mongo.findOne(Query.query(Criteria.where("_id").is(summonerDTO.getId())), SummonerDTO.class, "Summoner");

            // 새로운 소환사라면?
            boolean check = mongo.exists(Query.query(Criteria.where("_id").is(summonerDTO.getId())), "Summoner");
            if (!check) {
                System.out.println("New summoner registration");
                // SEARCH By id for tier info
                String tierById = "https://kr.api.riotgames.com/lol/league/v3/positions/by-summoner/";

                HttpEntity<Set<LeaguePositionDTO>> requestEn2 = setHeaders();
                ResponseEntity<Set> responseEn2 = restTemplate.exchange(tierById + String.valueOf(summonerDTO.getId()), HttpMethod.GET, requestEn2, Set.class);

                headers = requestEn2.getHeaders();

                Set<LeaguePositionDTO> positions = responseEn2.getBody();

                // SEARCH By accountId for recent 20 games info
                String recent = "https://kr.api.riotgames.com/lol/match/v3/matchlists/by-account/";
                HttpEntity<MatchListDTO> requestEn3 = setHeaders();
                ResponseEntity<MatchListDTO> responseEn3 = restTemplate.exchange(recent + String.valueOf(summonerDTO.getAccountId()) + "?endIndex=20", HttpMethod.GET, requestEn3,
                        MatchListDTO.class);

                headers = responseEn3.getHeaders();

                List<MatchReferenceDTO> matches = responseEn3.getBody().getMatches();

                // SET positions, matches IN summonerDTO
                summonerDTO.setPositions(positions);
                summonerDTO.setMatches(matches);


                // SEARCH By gameId for each match info ( 20 games )


                for (int i = 0; i < matches.size(); i++) {


                    String matchById = "https://kr.api.riotgames.com/lol/match/v3/matches/";


                    HttpEntity<MatchDTO> request = setHeaders();


                    ResponseEntity<MatchDTO> response = restTemplate.exchange(matchById + matches.get(i).getGameId(), HttpMethod.GET, request, MatchDTO.class);

                    headers = response.getHeaders();

                    MatchDTO match = response.getBody();
                    mongo.save(match, "Match");

                }


                // INSERT mongoDB
                mongo.save(summonerDTO, "Summoner");
            }

            // 갱신 될 정보가 있다면?
            else if (summonerDB.getRevisionDate() < summonerDTO.getRevisionDate()) {

                System.out.println("Summoner information Update ");
                // SEARCH By id for tier info
                String tierById = "https://kr.api.riotgames.com/lol/league/v3/positions/by-summoner/";

                HttpEntity<Set<LeaguePositionDTO>> requestEn2 = setHeaders();
                ResponseEntity<Set> responseEn2 = restTemplate.exchange(tierById + String.valueOf(summonerDTO.getId()), HttpMethod.GET, requestEn2, Set.class);

                headers = responseEn2.getHeaders();

                Set<LeaguePositionDTO> positions = responseEn2.getBody();

                // SEARCH By accountId for recent 20 games info
                String recent = "https://kr.api.riotgames.com/lol/match/v3/matchlists/by-account/";
                HttpEntity<MatchListDTO> requestEn3 = setHeaders();
                ResponseEntity<MatchListDTO> responseEn3 = restTemplate.exchange(recent + String.valueOf(summonerDTO.getAccountId()) + "?endIndex=20", HttpMethod.GET, requestEn3,
                        MatchListDTO.class);

                headers = responseEn3.getHeaders();

                List<MatchReferenceDTO> matches = responseEn3.getBody().getMatches();

                SummonerDTO temp = mongo.findOne(Query.query(Criteria.where("_id").is(summonerDTO.getId())), SummonerDTO.class, "Summoner");
                List<MatchReferenceDTO> matchesDB = temp.getMatches();

                for (int i = matches.size() - 1; i >= 0; i--) {
                    if (matches.get(i).getTimestamp() > matchesDB.get(0).getTimestamp()) {
                        matchesDB.add(0, matches.get(i));
                        System.out.println("NEW gameId : " + matches.get(i).getGameId() + "/ championId : " + matches.get(i).getChampion());
                    }
                }


                // SET positions, matches IN summonerDTO
                summonerDTO.setPositions(positions);
                summonerDTO.setMatches(matchesDB);


                // SEARCH By gameId for each match info ( 20 games )
                for (int i = 0; i < matches.size(); i++) {
                    String matchById = "https://kr.api.riotgames.com/lol/match/v3/matches/";

                    HttpEntity<MatchDTO> request = setHeaders();
                    ResponseEntity<MatchDTO> response = restTemplate.exchange(matchById + matches.get(i).getGameId(), HttpMethod.GET, request, MatchDTO.class);

                    headers = response.getHeaders();

                    MatchDTO match = response.getBody();


                    mongo.save(match, "Match");

                }


                // INSERT mongoDB
                mongo.save(summonerDTO, "Summoner");
            } else {
                System.out.println("No infomation to be updated");

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error!!!");
            System.out.println("headers : " + headers.toString());
        }
    }

    // Champion, Item, Spell Data update method (MANUAL)
    public void updateData() {


        HttpEntity<ChampionListDTO> requestC = setHeaders();
        String championList = "https://kr.api.riotgames.com/lol/static-data/v3/champions?locale=en_US&champListData=image&tags=image&dataById=false";
        ResponseEntity<ChampionListDTO> responseC = restTemplate.exchange(championList, HttpMethod.GET, requestC, ChampionListDTO.class);
        ChampionListDTO championListDTO = responseC.getBody();
        Map<String, ChampionDTO> cmap = championListDTO.getData();

        List<ChampionDTO> clist = new ArrayList<>(cmap.values());

        for (int i = 0; i < clist.size(); i++) {
            System.out.println(clist.get(i).getId() + " / " + clist.get(i).getName());
            mongo.save(clist.get(i), "Champion");
        }


        HttpEntity<ItemListDTO> requestI = setHeaders();
        String itemList = "https://kr.api.riotgames.com/lol/static-data/v3/items?locale=ko_KR&itemListData=image&tags=image";
        ResponseEntity<ItemListDTO> responseI = restTemplate.exchange(itemList, HttpMethod.GET, requestI, ItemListDTO.class);
        ItemListDTO itemListDTO = responseI.getBody();
        Map<String, ItemDTO> imap = itemListDTO.getData();

        List<ItemDTO> ilist = new ArrayList<>(imap.values());

        for (int i = 0; i < ilist.size(); i++) {
            System.out.println(ilist.get(i).getId() + " / " + ilist.get(i).getPlaintext());
            mongo.save(ilist.get(i), "Item");
        }


        HttpEntity<SummonerSpellListDTO> requestS = setHeaders();
        String spellList = "https://kr.api.riotgames.com/lol/static-data/v3/summoner-spells?locale=ko_KR&spellListData=image&dataById=false&tags=image";
        ResponseEntity<SummonerSpellListDTO> responseS = restTemplate.exchange(spellList, HttpMethod.GET, requestC, SummonerSpellListDTO.class);
        SummonerSpellListDTO summonerSpellListDTO = responseS.getBody();
        Map<String, SummonerSpellDTO> smap = summonerSpellListDTO.getData();

        List<SummonerSpellDTO> slist = new ArrayList<>(smap.values());

        for (int i = 0; i < slist.size(); i++) {
            System.out.println(slist.get(i).getId() + " / " + slist.get(i).getDescription());
            mongo.save(slist.get(i), "SummonerSpell");
        }

    }


}



