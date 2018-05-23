package com.springboot.analytics.dao;

import com.springboot.analytics.dto.*;

import java.util.List;

public interface UserDao {

    public SummonerDTO getSummoner(long summonerId);

    public MatchDTO getMatch(long gameId);

    public ChampionDTO getChampion(int championId);

    public void updateWinCount(int championId);

    public void updateLossCount(int championId);

    public SummonerSpellDTO getSpellOne(int spellOneId);

    public SummonerSpellDTO getSpellTwo(int spellTwoId);

    public void setMatchComplete(long gameId);

    public List<ChampionDTO> getChampionList();

    public void saveMatch(MatchDTO matchDTO);

    public void saveSummoner(SummonerDTO summonerDTO);

    public void saveChampion(ChampionDTO championDTO);

    public void saveItem(ItemDTO itemDTO);

    public void saveSpell(SummonerSpellDTO summonerSpellDTO);

    public boolean exist(long summonerId);

}
