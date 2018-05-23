package com.springboot.analytics.service;

import com.springboot.analytics.dto.ChampionDTO;
import com.springboot.analytics.dto.MatchDTO;
import com.springboot.analytics.dto.MostChampion;
import com.springboot.analytics.dto.SummonerDTO;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface UserService {

    public void insert(String name) throws HttpClientErrorException;

    public SummonerDTO read(String summonerName);

    public List<MatchDTO> setNames(SummonerDTO summonerDTO);

    public List<MatchDTO> setTotalValue(List<MatchDTO> matchList);

    public List<MostChampion> getMostChampion(List<MatchDTO> matchDTOList, long summonerId);

    public List<ChampionDTO> getChampionList();
}
