package com.ericlam.mc.rankcal.utils;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.rankcal.types.CalType;
import com.ericlam.mc.rankcal.utils.math.AdvMath;
import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.api.PlayerData;
import com.ericlam.mc.ranking.main.PvPRanking;

import java.util.List;
import java.util.stream.Collectors;

public class Normalization {
    private List<PlayerData> ints;
    private double min;
    private double max;
    private ArrayCalculation cal;

    /**
     * @param data player data list
     */
    public Normalization(List<PlayerData> data) {
        this.ints = data.stream().filter(d->d.getPlays() >= (int)PvPRanking.getConfigData("required-plays")).collect(Collectors.toList());
        for (PlayerData i : ints) {
            max = Math.max(i.getFinalScores(),max);
            min = Math.min(i.getFinalScores(),min);
        }
        cal = new ArrayCalculation(ints.stream().mapToDouble(PlayerData::getFinalScores).toArray());
    }

    /**
     *
     * @param new_min new min value
     * @param new_max new max value
     * @param data player data
     * @return rank data
     */
    public RankData minMaxNormalizeSingle(int new_min,int new_max,PlayerData data){
        double v = data.getFinalScores();
        double result = (v - min) / (max - min) * (new_max - new_min) + new_min;
        String rank = RankDataManager.getRank(CalType.MIN_MAX,AdvMath.round(2,result));
        return new RankData(data, rank, AdvMath.round(2,result));
    }

    /**
     *
     * @param new_min new min value
     * @param new_max new max value
     * @return rank data array
     */
    public RankData[] minMaxNormalize(int new_min,int new_max){
        RankData[] normalized = new RankData[ints.size()];
        for (int i = 0; i < ints.size();i++) {
            PlayerData data = ints.get(i);
            normalized[i] = minMaxNormalizeSingle(new_min,new_max,data);
        }
        return normalized;
    }

    /**
     *
     * @param data player data
     * @return rank data
     */
    public RankData zScoreNormalizeSingle(PlayerData data){
        double v = data.getFinalScores();
        double result = (v - cal.getMean()) / cal.getSd();
        String rank = RankDataManager.getRank(CalType.Z_SCORE,AdvMath.round(2,result));
        return new RankData(data,rank, AdvMath.round(2,result));
    }

    /**
     *
     * @return rank data array
     */
    public RankData[] zScoreNormalize(){
        RankData[] normalized = new RankData[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            PlayerData data = ints.get(i);
            normalized[i] = zScoreNormalizeSingle(data);
        }
        return normalized;
    }

    /**
     * @deprecated decimal Scaling is not support for rank calcuation now
     * @return rank data array
     */
    @Deprecated
    public RankData[] decimalScaling(){
        RankData[] normalized = new RankData[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            PlayerData data = ints.get(i);
            double v = data.getFinalScores();
            double result = v / Math.pow(10,AdvMath.getNumLenght(max));
            normalized[i] = new RankData(data, "", AdvMath.round(2,result));
        }
        return normalized;
    }

    public ArrayCalculation getCal() {
        return cal;
    }
}
