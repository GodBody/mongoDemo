
package com.springboot.analytics.controller;

import com.springboot.analytics.dto.*;
import com.springboot.analytics.service.UserService;
import com.springboot.analytics.service.UserServiceImpl;
import com.springboot.analytics.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Collections;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/main")
    public String main() {
        return "/main";
    }

    @GetMapping(value = "/search")
    public String search(@RequestParam(value = "summonerName") String name, Model model) throws Exception {

        logger.debug("검색된 소환사 닉네임 : " + name);

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

