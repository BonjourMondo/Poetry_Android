package com.example.sf.TimeShaft;

import java.util.ArrayList;

/**
 * Created by sf on 17-12-11.
 * 用于记录显示在地图上的一个mark点对应的诗词信息
 */

public class MarkerPoetryInfo {
//    诗人到达该地点的年龄
    private int ageArrive;
//    诗人离开该地点的年龄
    private int ageLeave;
//    在此地做的诗的数目；
    private int poetryNum;
//    在此地产生的数据库记录的id(有些记录中可能没有诗，但是仍然记录)
    ArrayList<Integer> poetriesId;

//    在此地的诗词的名字
    ArrayList<String> poetriesName;

    public MarkerPoetryInfo() {
        poetryNum=0;
        poetriesId=new ArrayList<>();
        poetriesName=new ArrayList<>();
    }
    public int getAgeArrive() {
        return ageArrive;
    }

    public void setAgeArrive(int ageArrive) {
        this.ageArrive = ageArrive;
    }

    public int getAgeLeave() {
        return ageLeave;
    }

    public void setAgeLeave(int ageLeave) {
        this.ageLeave = ageLeave;
    }

    public int getPoetryNum() {
        return poetryNum;
    }

    public void addPoetryNum(int poetryNum) {
        this.poetryNum += poetryNum;
    }

    public ArrayList<Integer> getPoetriesId() {
        return poetriesId;
    }
    public ArrayList<String> getPoetriesName(){
        return poetriesName;
    }
    public Poetries getPoetrise(){
        return new Poetries(poetriesId,poetriesName);
    }

    public void setPoetriesId(ArrayList<Integer> poetriesId) {
        this.poetriesId = poetriesId;
    }
}
