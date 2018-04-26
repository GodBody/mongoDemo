package com.springboot.springbootdemo.controller;

import com.springboot.springbootdemo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Controller
public class HomeController {

    private static RestTemplate restTemplate = new RestTemplate();
    private static RetryTemplate retryTemplate = new RetryTemplate();

    @Autowired
    private MongoTemplate mongo;

    public static <T> HttpEntity<T> setHeaders() {
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Origin", "https://developer.riotgames.com");
        headers.set("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.set("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML,like Gecko) Chrome/64.0.3282.186 Safari/537.36");
        headers.set("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.set("X-Riot-Token", "RGAPI-296b0cb7-bd55-4315-b160-fd29327b64b6");
        return new HttpEntity<T>(headers);

    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public void index() {
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@RequestParam(value = "summonerName") String name, Model model) throws Exception {
        System.out.println("UPDATE new summoner into mongoDB ");
        insert(name);

        return "search";
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "summonerName") String name, Model model) throws Exception {

        System.out.println();
        System.out.println("검색된 소환사 : " + name);

        insert(name);

        SummonerDTO summoner = mongo.findOne(Query.query(Criteria.where("name").is(name)), SummonerDTO.class, "Summoner");


        List<MatchDTO> matchList = new ArrayList<MatchDTO>();
        System.out.println("검색된 게임 수 : " + summoner.getMatches().size());
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
                        System.out.println("승리 카운트 !! " + p.getChampionName() + " / " + p.getStats().isWin());
                    } else {
                        mongo.updateFirst(Query.query(Criteria.where("_id").is(p.getChampionId())), new Update().inc("lossCount", 1), "Champion");
                        System.out.println("패배 카운트 !! " + p.getChampionName() + " / " + p.getStats().isWin());
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


        List<MatchDTO> tempList = new ArrayList<>();
        System.out.println("matchList Size : " + matchList.size());
        for (int i = 0; i < matchList.size(); i++) {
            MatchDTO match = matchList.get(i);
            for (int j = 0; j < match.getParticipantIdentities().size(); j++) {
                if (match.getParticipantIdentities().get(j).getPlayer().getSummonerName().equals(name)) {
                    MatchDTO tempDTO = new MatchDTO();
                    tempDTO.setParticipants(new ArrayList<>());

                    tempDTO.getParticipants().add(matchList.get(i).getParticipants().get(j));
                    tempList.add(tempDTO);
                }
            }

        }


        Map<String, MostChampion> map = new HashMap<>();
        List<MostChampion> list = new ArrayList<>();

        System.out.println("tempList Size : " + tempList.size());
        for (int i = 0; i < tempList.size(); i++) {
            for (int j = 0; j < tempList.get(i).getParticipants().size(); j++) {

                String cName = tempList.get(i).getParticipants().get(j).getChampionName();
                int cId = tempList.get(i).getParticipants().get(j).getChampionId();

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
                    ;
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
                    else if(o1.getWinRate() < o2.getWinRate())
                        return 1;
                    else {
                        if(o1.getKdaRatio() > o2.getKdaRatio())
                            return -1;
                        else
                            return 1;
                    }

                }
            }
        });

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getChampionName() + "/" + list.get(i).getGameCount() + "/" + list.get(i).getWinRate());
        }

        List<ChampionDTO> championList = mongo.findAll(ChampionDTO.class, "Champion");

        Collections.sort(championList, new Comparator<ChampionDTO>() {
            @Override
            public int compare(ChampionDTO o1, ChampionDTO o2) {
                if (o1.getWinCount() + o1.getLossCount() > o2.getWinCount() + o2.getLossCount())
                    return -1;
                else
                    return 1;
            }
        });

        model.addAttribute("championList", championList);
        model.addAttribute("mostList", list);
        model.addAttribute("tempList", tempList);
        model.addAttribute("summoner", summoner);
        model.addAttribute("matchList", matchList);
        model.addAttribute("summonerName", name);

        return "search";
    }


    public void insert(String name) throws HttpClientErrorException {
        HttpHeaders headers = null;

        /*

        챔피언 정보 등록 부분 (1회성)

        HttpEntity<ChampionListDTO> requestC = setHeaders();
        String championList = "https://kr.api.riotgames.com/lol/static-data/v3/champions?locale=en_US&champListData=image&tags=image&dataById=false";
        ResponseEntity<ChampionListDTO> responseC = restTemplate.exchange(championList, HttpMethod.GET, requestC, ChampionListDTO.class);
        ChampionListDTO cList = responseC.getBody();
        Map<String, ChampionDTO> cmap = cList.getData();

        List<ChampionDTO> list = new ArrayList<>(cmap.values());

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId() + " / " + list.get(i).getName());
            mongo.save(list.get(i), "Champion");
        }

        */

        /*

        아이템 정보 등록 부분

        HttpEntity<ItemListDTO> requestC = setHeaders();
        String itemList = "https://kr.api.riotgames.com/lol/static-data/v3/items?locale=ko_KR&itemListData=image&tags=image&api_key=RGAPI-907abf3b-08ed-4370-b63f-a80246800064";
        ResponseEntity<ItemListDTO> responseC = restTemplate.exchange(itemList, HttpMethod.GET, requestC, ItemListDTO.class);
        ItemListDTO iList = responseC.getBody();
        Map<String, ItemDTO> imap = iList.getData();

        List<ItemDTO> list = new ArrayList<>(imap.values());

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId() + " / " + list.get(i).getPlaintext());
            mongo.save(list.get(i), "Item");
        }
        */

        /*

        스펠 정보 등록 부분

        HttpEntity<SummonerSpellListDTO> requestC = setHeaders();
        String spellList = "https://kr.api.riotgames.com/lol/static-data/v3/summoner-spells?locale=ko_KR&spellListData=image&dataById=false&tags=image&api_key=RGAPI-907abf3b-08ed-4370-b63f-a80246800064";
        ResponseEntity<SummonerSpellListDTO> responseC = restTemplate.exchange(spellList, HttpMethod.GET, requestC, SummonerSpellListDTO.class);
        SummonerSpellListDTO sList = responseC.getBody();
        Map<String, SummonerSpellDTO> smap = sList.getData();

        List<SummonerSpellDTO> list = new ArrayList<>(smap.values());

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId() + " / " + list.get(i).getDescription());
            mongo.save(list.get(i), "SummonerSpell");
        }
        */
        try {
            // SEARCH By Name
            String searchByName = "https://kr.api.riotgames.com/lol/summoner/v3/summoners/by-name/";

            HttpEntity<SummonerDTO> requestEn = setHeaders();
            ResponseEntity<SummonerDTO> responseEn = restTemplate.exchange(searchByName + name, HttpMethod.GET, requestEn,
                    SummonerDTO.class);

            headers = responseEn.getHeaders();

            SummonerDTO summonerDTO = responseEn.getBody();

            SummonerDTO summonerDB = mongo.findOne(Query.query(Criteria.where("name").is(name)), SummonerDTO.class, "Summoner");

            // 새로운 소환사라면?
            boolean check = mongo.exists(Query.query(Criteria.where("name").is(name)), "Summoner");
            if (!check) {
                System.out.println("새로운 정보 등록");
                // SEARCH By id for tier info
                String tierById = "https://kr.api.riotgames.com/lol/league/v3/positions/by-summoner/";

                HttpEntity<Set<LeaguePositionDTO>> requestEn2 = setHeaders();
                ResponseEntity<Set> responseEn2 = restTemplate.exchange(tierById + String.valueOf(summonerDTO.getId()), HttpMethod.GET, requestEn2, Set.class);

                headers = requestEn2.getHeaders();

                Set<LeaguePositionDTO> positions = responseEn2.getBody();

                // SEARCH By accountId for recent 20 games info
                String recent = "https://kr.api.riotgames.com/lol/match/v3/matchlists/by-account/";
                HttpEntity<MatchListDTO> requestEn3 = setHeaders();
                ResponseEntity<MatchListDTO> responseEn3 = restTemplate.exchange(recent + String.valueOf(summonerDTO.getAccountId()) + "?endIndex=30", HttpMethod.GET, requestEn3,
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

                System.out.println("새로운 정보 갱신");
                // SEARCH By id for tier info
                String tierById = "https://kr.api.riotgames.com/lol/league/v3/positions/by-summoner/";

                HttpEntity<Set<LeaguePositionDTO>> requestEn2 = setHeaders();
                ResponseEntity<Set> responseEn2 = restTemplate.exchange(tierById + String.valueOf(summonerDTO.getId()), HttpMethod.GET, requestEn2, Set.class);

                headers = responseEn2.getHeaders();

                Set<LeaguePositionDTO> positions = responseEn2.getBody();

                // SEARCH By accountId for recent 20 games info
                String recent = "https://kr.api.riotgames.com/lol/match/v3/matchlists/by-account/";
                HttpEntity<MatchListDTO> requestEn3 = setHeaders();
                ResponseEntity<MatchListDTO> responseEn3 = restTemplate.exchange(recent + String.valueOf(summonerDTO.getAccountId()) + "/recent", HttpMethod.GET, requestEn3,
                        MatchListDTO.class);

                headers = responseEn3.getHeaders();

                List<MatchReferenceDTO> matches = responseEn3.getBody().getMatches();

                SummonerDTO temp = mongo.findOne(Query.query(Criteria.where("_id").is(summonerDTO.getId())), SummonerDTO.class, "Summoner");
                List<MatchReferenceDTO> matchesDB = temp.getMatches();

                for (int i = matches.size() - 1; i >= 0; i--) {
                    if (matches.get(i).getTimestamp() > matchesDB.get(0).getTimestamp()) {
                        matchesDB.add(0, matches.get(i));
                        System.out.println("새로운 gameId : " + matches.get(i).getGameId() + "/ champion : " + matches.get(i).getChampion());
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
                System.out.println("갱신될 정보가 없습니다.");

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error!!!");
            System.out.println("headers : " + headers.toString());
        }
    }


}



