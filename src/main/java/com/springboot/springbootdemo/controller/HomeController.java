package com.springboot.springbootdemo.controller;

import com.google.gson.Gson;
import com.springboot.springbootdemo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    private static RestTemplate restTemplate = new RestTemplate();

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
        headers.set("X-Riot-Token", "RGAPI-4c4249d6-7c94-4705-922b-ebe88b6bf528");
        return new HttpEntity<T>(headers);

    }

    @RequestMapping(value = "/main")
    public String index() {
        return "main";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "summonerName") String name) throws Exception {

        // search By Name
        String searchByName = "https://kr.api.riotgames.com/lol/summoner/v3/summoners/by-name/";
        System.out.println("summonerName : " + name);

        HttpEntity<SummonerDTO> requestEn = setHeaders();
        ResponseEntity<SummonerDTO> responseEn = restTemplate.exchange(searchByName + name, HttpMethod.GET, requestEn,
                SummonerDTO.class);

        SummonerDTO summonerDTO = responseEn.getBody();
        boolean check = mongo.exists(Query.query(Criteria.where("accountId").is(summonerDTO.getAccountId())), "summoner");
        if (check == true) {
            mongo.save(summonerDTO, "summoner");
            System.out.println("기존 소환사 수정");
        } else if (check == false) {
            mongo.insert(summonerDTO, "summoner");
            System.out.println("새로운 소환사 추가");
        }

//        Long accountId = summonerDTO.getAccountId();

        // search By id for tier info
        String tierById = "https://kr.api.riotgames.com/lol/league/v3/positions/by-summoner/";

        HttpEntity<Set<LeaguePositionDTO>> requestEn2 = setHeaders();
        ResponseEntity<Set> responseEn2 = restTemplate.exchange(tierById + String.valueOf(summonerDTO.getId()), HttpMethod.GET, requestEn2, Set.class);
        Set<LeaguePositionDTO> position = responseEn2.getBody();
        System.out.println("position : " + position.toString());
        mongo.upsert(Query.query(Criteria.where("accountId").is(summonerDTO.getAccountId())), new Update().push("position", position), "summoner");

        // search By accountId for recent 20 games info
        String recent = "https://kr.api.riotgames.com/lol/match/v3/matchlists/by-account/";
        HttpEntity<MatchListDTO> requestEn3 = setHeaders();
        ResponseEntity<MatchListDTO> responseEn3 = restTemplate.exchange(recent + String.valueOf(summonerDTO.getAccountId()) + "/recent", HttpMethod.GET, requestEn3,
                MatchListDTO.class);

        List<MatchReferenceDTO> matches = responseEn3.getBody().getMatches();
        MatchReferenceDTO[] array = matches.toArray(new MatchReferenceDTO[matches.size()]);

        // search By accountId for each match info (20 games)

        for (int i = 0; i < array.length; i++) {

            HttpEntity<MatchDTO> request = setHeaders();
            String matchById = "https://kr.api.riotgames.com/lol/match/v3/matches/";
            ResponseEntity<MatchDTO> response = restTemplate.exchange(matchById + array[i].getGameId(), HttpMethod.GET, request, MatchDTO.class);

            MatchDTO match = response.getBody();
            array[i].setMatch(match);

            mongo.updateFirst(Query.query(Criteria.where("accountId").is(summonerDTO.getAccountId())),
                    new Update().addToSet("matches", array[i]), "summoner");


        }
        List<String> obj = mongo.findAll(String.class);

        System.out.println(obj.size());

        return "";
    }
}

