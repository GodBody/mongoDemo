package com.springboot.analytics.util;

import com.springboot.analytics.dto.ChampionDTO;

import java.util.Comparator;

public class CompChampion implements Comparator<ChampionDTO> {

    @Override
    public int compare(ChampionDTO o1, ChampionDTO o2) {

        if (o1.getWinCount() + o1.getLossCount() > o2.getWinCount() + o2.getLossCount()) {
            return -1;
        }

        else if (o1.getWinCount() + o1.getLossCount() == o2.getWinCount() + o2.getLossCount()) {

            if (o1.getWinCount() > o2.getWinCount()) {
                return -1;
            }

            else {
                return 1;
            }
        }

        else {
            return 1;
        }
    }
}
