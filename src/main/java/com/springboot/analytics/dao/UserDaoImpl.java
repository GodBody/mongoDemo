package com.springboot.analytics.dao;

import com.springboot.analytics.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoTemplate mongo;

    @Override
    public SummonerDTO getSummoner(long summonerId) {
        return mongo.findOne(Query.query(Criteria.where("_id").is(summonerId)), SummonerDTO.class, "Summoner");
    }

    @Override
    public MatchDTO getMatch(long gameId) {
        return mongo.findOne(Query.query(Criteria.where("_id").is(gameId)), MatchDTO.class, "Match");
    }

    @Override
    public ChampionDTO getChampion(int championId) {
        return mongo.findOne(Query.query(Criteria.where("_id").is(championId)), ChampionDTO.class, "Champion");
    }

    @Override
    public void updateWinCount(int championId) {
        mongo.updateFirst(Query.query(Criteria.where("_id").is(championId)), new Update().inc("winCount", 1), "Champion");
    }

    @Override
    public void updateLossCount(int championId) {
        mongo.updateFirst(Query.query(Criteria.where("_id").is(championId)), new Update().inc("lossCount", 1), "Champion");
    }

    @Override
    public SummonerSpellDTO getSpellOne(int spellOneId) {
        return mongo.findOne(Query.query(Criteria.where("_id").is(spellOneId)), SummonerSpellDTO.class, "SummonerSpell");

    }

    @Override
    public SummonerSpellDTO getSpellTwo(int spellTwoId) {
        return mongo.findOne(Query.query(Criteria.where("_id").is(spellTwoId)), SummonerSpellDTO.class, "SummonerSpell");

    }

    @Override
    public void setMatchComplete(long gameId) {
        mongo.updateFirst(Query.query(Criteria.where("_id").is(gameId)), new Update().set("complete", true), "Match");
    }

    @Override
    public List<ChampionDTO> getChampionList() {
        return mongo.findAll(ChampionDTO.class, "Champion");

    }

    @Override
    public void saveMatch(MatchDTO matchDTO) {
        mongo.save(matchDTO, "Match");
    }

    @Override
    public void saveSummoner(SummonerDTO summonerDTO) {
        mongo.save(summonerDTO, "Summoner");
    }

    @Override
    public void saveChampion(ChampionDTO championDTO) {
        mongo.save(championDTO, "Champion");
    }

    @Override
    public void saveItem(ItemDTO itemDTO) {
        mongo.save(itemDTO, "Item");
    }

    @Override
    public void saveSpell(SummonerSpellDTO summonerSpellDTO) {
        mongo.save(summonerSpellDTO, "SummonerSpell");
    }

    @Override
    public boolean exist(long summonerId) {
        return mongo.exists(Query.query(Criteria.where("_id").is(summonerId)), "Summoner");
    }

}
