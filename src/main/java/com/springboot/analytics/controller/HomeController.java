
package com.springboot.analytics.controller;

import com.springboot.analytics.dto.*;
import com.springboot.analytics.service.UserService;
import com.springboot.analytics.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Collections;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public void index() {
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "summonerName") String name, Model model) throws Exception {

        System.out.println("검색된 소환사 닉네임 : " + name);

        userService.insert(name);

        SummonerDTO summonerDTO = userService.read(name);

        List<MatchDTO> matchList;
        List<MatchDTO> matchDTOList;

        matchList = userService.setNames(summonerDTO);
        matchDTOList = userService.setTotalValue(matchList);

        List<MostChampion> mostChampionList = userService.getMostChampion(matchDTOList, summonerDTO.getId());
        Collections.sort(mostChampionList, new CompMostChampion());

        List<ChampionDTO> championList = userService.getChampionList();
        Collections.sort(championList, new CompChampion());

        model.addAttribute("summoner", summonerDTO);
        model.addAttribute("summonerName", name);
        model.addAttribute("matchList", matchList);
        model.addAttribute("mostList", mostChampionList);
        model.addAttribute("championList", championList);


        return "search";
    }
}

