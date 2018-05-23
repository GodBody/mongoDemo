package com.springboot.analytics.util;

import com.springboot.analytics.dto.MostChampion;

import java.util.Comparator;

public class CompMostChampion implements Comparator<MostChampion> {

    @Override
    public int compare(MostChampion o1, MostChampion o2) {

        if (o1.getGameCount() > o2.getGameCount()) {
            return -1;
        }

        else if (o1.getGameCount() < o2.getGameCount()) {
            return 1;
        }

        else {

            if (o1.getWinRate() > o2.getWinRate()) {
                return -1;
            }

            else if (o1.getWinRate() < o2.getWinRate()) {
                return 1;
            }

            else {

                if (o1.getKdaRatio() > o2.getKdaRatio()) {
                    return -1;
                }

                else {
                    return 1;
                }
            }
        }
    }
}
