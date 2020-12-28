package com.springboot.analytics.service;

import com.springboot.analytics.dao.UserDao;
import com.springboot.analytics.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String RIOTGAMES_URL = "https://developer.riotgames.com";

    private static final String SEARCH_BY_NAME_URL = "https://kr.api.riotgames.com/lol/summoner/v3/summoners/by-name/";
    private static final String TIER_BY_ID_URL = "https://kr.api.riotgames.com/lol/league/v3/positions/by-summoner/";
    private static final String SEARCH_BY_ID_RECENT_20_GAMES = "https://kr.api.riotgames.com/lol/match/v3/matchlists/by-account/";
    private static final String MATCH_BY_ID = "https://kr.api.riotgames.com/lol/match/v3/matches/";

    private static final String X_Riot_Token = "X-Riot-Token";

    private static final String API_KEY = "RGAPI-8d48175c-b265-4309-8bed-07fc3eb7ccce";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    public static <T> HttpEntity<T> setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ORIGIN, RIOTGAMES_URL);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.set(X_Riot_Token, API_KEY);

        return new HttpEntity<T>(headers);
    }

    @Override
    public void insert(String name) throws HttpClientErrorException {

        try {

            RestTemplate restTemplate = restTemplateBuilder.build();
            // 유저 닉네임으로 검색

            HttpEntity<SummonerDTO> httpEntity = setHeaders();
            ResponseEntity<SummonerDTO> responseEntity = restTemplate.exchange(SEARCH_BY_NAME_URL + name, HttpMethod.GET, httpEntity, SummonerDTO.class);

            SummonerDTO summonerDTO = responseEntity.getBody();

            SummonerDTO summonerFromDB = userDao.getSummoner(summonerDTO.getId());

            // DB에 등록되있는지 체크하는 flag
            boolean check = userDao.exist(summonerDTO.getId());

            // 처음으로 검색된 유저라면 DB에 등록한다.
            if (!check) {

                System.out.println("새로운 소환사 등록");
                // 유저의 랭킹 정보


                HttpEntity<Set<LeaguePositionDTO>> requestEn2 = setHeaders();
                ResponseEntity<Set> responseEn2 = restTemplate.exchange(TIER_BY_ID_URL + String.valueOf(summonerDTO.getId()), HttpMethod.GET, requestEn2, Set.class);

                Set<LeaguePositionDTO> positions = responseEn2.getBody();

                // 유저의 고유한 ID로 최근 20 게임 플레이 리스트를 가져온다.

                HttpEntity<MatchListDTO> requestEn3 = setHeaders();
                ResponseEntity<MatchListDTO> responseEn3 = restTemplate.exchange(SEARCH_BY_ID_RECENT_20_GAMES + String.valueOf(summonerDTO.getAccountId()) + "?endIndex=20", HttpMethod.GET, requestEn3,
                        MatchListDTO.class);

                List<MatchReferenceDTO> matches = responseEn3.getBody().getMatches();

                // SET positions, matches IN summonerDTO
                summonerDTO.setPositions(positions);
                summonerDTO.setMatches(matches);

                // 위에서 가져온 플레이 리스트의 각 게임마다의 상세 정보를 가져와서 DB에 저장.
                for (int i = 0; i < matches.size(); i++) {


                    HttpEntity<MatchDTO> request = setHeaders();
                    ResponseEntity<MatchDTO> response = restTemplate.exchange(MATCH_BY_ID + matches.get(i).getGameId(), HttpMethod.GET, request, MatchDTO.class);


                    MatchDTO match = response.getBody();
                    userDao.saveMatch(match);
                }

                userDao.saveSummoner(summonerDTO);
            }

            // 기존에 등록되있지만 새로운 정보가 갱신될 부분이 있다면? 새로운 부분만 가져와서 DB에 등록한다.
            else if (summonerFromDB.getRevisionDate() < summonerDTO.getRevisionDate()) {

                System.out.println("게임 데이터 갱신");


                HttpEntity<Set<LeaguePositionDTO>> requestEn2 = setHeaders();
                ResponseEntity<Set> responseEn2 = restTemplate.exchange(TIER_BY_ID_URL + String.valueOf(summonerDTO.getId()), HttpMethod.GET, requestEn2, Set.class);


                Set<LeaguePositionDTO> positions = responseEn2.getBody();

                HttpEntity<MatchListDTO> requestEn3 = setHeaders();
                ResponseEntity<MatchListDTO> responseEn3 = restTemplate.exchange(SEARCH_BY_ID_RECENT_20_GAMES + String.valueOf(summonerDTO.getAccountId()) + "?endIndex=20", HttpMethod.GET, requestEn3,
                        MatchListDTO.class);

                List<MatchReferenceDTO> matches = responseEn3.getBody().getMatches();

                SummonerDTO summonerDTO2 = userDao.getSummoner(summonerDTO.getId());
                List<MatchReferenceDTO> matchesDB = summonerDTO2.getMatches();

                for (int i = matches.size() - 1; i >= 0; i--) {
                    if (matches.get(i).getTimestamp() > matchesDB.get(0).getTimestamp()) {
                        matchesDB.add(0, matches.get(i));
                    }
                }
                summonerDTO.setPositions(positions);
                summonerDTO.setMatches(matchesDB);

                for (int i = 0; i < matches.size(); i++) {


                    HttpEntity<MatchDTO> request = setHeaders();
                    ResponseEntity<MatchDTO> response = restTemplate.exchange(MATCH_BY_ID + matches.get(i).getGameId(), HttpMethod.GET, request, MatchDTO.class);


                    MatchDTO matchDTO = response.getBody();

                    userDao.saveMatch(matchDTO);

                }
                userDao.saveSummoner(summonerDTO);
            } else {
                System.out.println("최신 정보입니다. 갱신이 필요한 부분이 없습니다.");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SummonerDTO read(String summonerName) {

        RestTemplate restTemplate = restTemplateBuilder.build();

        HttpEntity<SummonerDTO> requestEn = setHeaders();
        ResponseEntity<SummonerDTO> responseEn = restTemplate.exchange(SEARCH_BY_NAME_URL + summonerName, HttpMethod.GET, requestEn,
                SummonerDTO.class);

        long summonerId = responseEn.getBody().getId();

        SummonerDTO summonerDTO = userDao.getSummoner(summonerId);

        return summonerDTO;
    }

    @Override
    public List<MatchDTO> setNames(SummonerDTO summonerDTO) {

        List<MatchDTO> matchList = new ArrayList<>();

        for (int i = 0; i < summonerDTO.getMatches().size(); i++) {

            long gameId = summonerDTO.getMatches().get(i).getGameId();

            MatchDTO matchDTO = userDao.getMatch(gameId);

            for (int j = 0; j < matchDTO.getParticipants().size(); j++) {

                ParticipantDTO participantDTO = matchDTO.getParticipants().get(j);

                participantDTO.getStats().setKdaRatio();

                int championId = participantDTO.getChampionId();

                int spellOneId = participantDTO.getSpell1Id();
                int spellTwoId = participantDTO.getSpell2Id();

                ChampionDTO championDTO = userDao.getChampion(championId);

                participantDTO.setChampionName(championDTO.getImage().getFull());

                if (!matchDTO.isComplete()) {
                    if (participantDTO.getStats().isWin()) {
                        userDao.updateWinCount(participantDTO.getChampionId());
                    } else {
                        userDao.updateLossCount(participantDTO.getChampionId());
                    }
                }
                SummonerSpellDTO spellOne = userDao.getSpellOne(spellOneId);
                SummonerSpellDTO spellTwo = userDao.getSpellTwo(spellTwoId);

                if (spellOne == null || spellTwo == null) {
                } else {
                    participantDTO.setSpell1Name(spellOne.getImage().getFull());
                    participantDTO.setSpell2Name(spellTwo.getImage().getFull());
                }
            }
            userDao.setMatchComplete(gameId);

            matchList.add(matchDTO);
        }

        return matchList;
    }

    @Override
    public List<MatchDTO> setTotalValue(List<MatchDTO> matchList) {

        for (int i = 0; i < matchList.size(); i++) {

            MatchDTO matchDTO = matchList.get(i);

            int totalKill = 0;
            int totalDeath = 0;
            int totalAssist = 0;
            int totalDamageDealtToChampions = 0;
            int totalDamageTaken = 0;

            for (int j = 0; j < matchDTO.getParticipants().size(); j++) {

                ParticipantDTO participantDTO = matchDTO.getParticipants().get(j);

                if (j <= 4) {
                    totalKill += participantDTO.getStats().getKills();
                    totalDeath += participantDTO.getStats().getDeaths();
                    totalAssist += participantDTO.getStats().getAssists();
                    totalDamageDealtToChampions += participantDTO.getStats().getTotalDamageDealtToChampions();
                    totalDamageTaken += participantDTO.getStats().getTotalDamageTaken();

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
                    totalKill += participantDTO.getStats().getKills();
                    totalDeath += participantDTO.getStats().getDeaths();
                    totalAssist += participantDTO.getStats().getAssists();
                    totalDamageDealtToChampions += participantDTO.getStats().getTotalDamageDealtToChampions();
                    totalDamageTaken += participantDTO.getStats().getTotalDamageTaken();


                    matchDTO.getTeams().get(1).setTotalKills(totalKill);
                    matchDTO.getTeams().get(1).setTotalDeaths(totalDeath);
                    matchDTO.getTeams().get(1).setTotalAssist(totalAssist);
                    matchDTO.getTeams().get(1).setTotalDamageDealtToChampions(totalDamageDealtToChampions);
                    matchDTO.getTeams().get(0).setTotalDamageTaken(totalDamageTaken);
                }
            }
        }
        return matchList;
    }

    @Override
    public List<MostChampion> getMostChampion(List<MatchDTO> matchDTOList, long summonerId) {

        List<MatchDTO> tempList = new ArrayList<>();

        for (int i = 0; i < matchDTOList.size(); i++) {

            MatchDTO matchDTO = matchDTOList.get(i);

            for (int j = 0; j < matchDTO.getParticipantIdentities().size(); j++) {

                if (matchDTO.getParticipantIdentities().get(j).getPlayer().getSummonerId() == summonerId) {
                    MatchDTO tempDTO = new MatchDTO();
                    tempDTO.setParticipants(new ArrayList<>());

                    tempDTO.getParticipants().add(matchDTOList.get(i).getParticipants().get(j));
                    tempList.add(tempDTO);
                }
            }
        }

        Map<String, MostChampion> mostChampionMap = new HashMap<>();
        List<MostChampion> mostChampionList = new ArrayList<>();

        for (int i = 0; i < tempList.size(); i++) {

            for (int j = 0; j < tempList.get(i).getParticipants().size(); j++) {

                String cName = tempList.get(i).getParticipants().get(j).getChampionName();

                boolean isWin = tempList.get(i).getParticipants().get(j).getStats().isWin();
                int kill = tempList.get(i).getParticipants().get(j).getStats().getKills();
                int death = tempList.get(i).getParticipants().get(j).getStats().getDeaths();
                int assist = tempList.get(i).getParticipants().get(j).getStats().getAssists();

                MostChampion mostChampion = new MostChampion();

                if (mostChampionMap.containsKey(cName)) {
                    MostChampion get = mostChampionMap.get(cName);
                    get.addGameCount();

                    if (isWin) {
                        get.addWinCount();
                    }
                    get.setLooseCount();
                    get.addKill(kill);
                    get.addDeath(death);
                    get.addAssist(assist);

                    mostChampionMap.put(cName, get);

                } else {
                    mostChampion.setChampionName(cName);
                    mostChampion.setGameCount(1);

                    if (isWin) {
                        mostChampion.setWinCount(1);
                    }
                    mostChampion.setLooseCount();
                    mostChampion.setKill(kill);
                    mostChampion.setDeath(death);
                    mostChampion.setAssist(assist);

                    mostChampionMap.put(cName, mostChampion);
                }
            }
        }

        Iterator<String> itr = mostChampionMap.keySet().iterator();

        while (itr.hasNext()) {

            String key = itr.next();

            mostChampionMap.get(key).setKdaRatio();
            mostChampionMap.get(key).setWinRate();

            mostChampionList.add(mostChampionMap.get(key));
        }

        return mostChampionList;
    }

    @Override
    public List<ChampionDTO> getChampionList() {
        return userDao.getChampionList();
    }
}
