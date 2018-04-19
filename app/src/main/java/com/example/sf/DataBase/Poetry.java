package com.example.sf.DataBase;

import android.util.Log;

import com.amap.api.maps.model.LatLng;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sf on 17-12-7.
 * 存储一首诗的信息
 */

public class Poetry extends DataSupport{
    /**
     * excel表中第一行中的题头的命名标准，用于在读取表的时候确定相应的列名在第几列*/
    public static final ArrayList<String> FirstLine= new ArrayList<String>(
            Arrays.asList(
                    ContextInFirstLine.TITLE_OF_POETRY,
                    "创作时间",
                    "内容",
                    "经度",
                    "维度",
                    ContextInFirstLine.AGE_OF_POET,
                    ContextInFirstLine.CITY_OF_ANCIENT,
                    ContextInFirstLine.CITY_OF_CURRENT,
                    ContextInFirstLine.COUNTY_OF_ANCIENT,
                    ContextInFirstLine.COUNTY_OF_CURRENT)
    );
    private Poet poet;
    private int Poet_id;
private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoet_id() {
        return Poet_id;
    }

    public void setPoet_id(int poet_id) {
        Poet_id = poet_id;
    }

    private String title;
//    创作时间
    private Date date;
//    诗词内容
    private String context;
//    坐标
    private double latitude=-1;
    private double longtitude=-1;
    private String city_ancient;
    private String county_ancient;
    private String city_current;
    private String county_current;
    private int ageOfPoet;
public Poetry(){


}
public static List<LatLng> getLatLngs(List<Poetry> poetries){
    List<LatLng> latLngs=new ArrayList<>();
    int size=poetries.size();
    for(int i=0;i<size;i++){
        Poetry poetry=poetries.get(i);
        latLngs.add(new LatLng(poetry.getLatitude(),poetry.getLongtitude()));
    }
    return latLngs;
}
public static LatLng getLatlng(Poetry poetry){
    LatLng latLng=new LatLng(poetry.getLatitude(),poetry.getLongtitude());
    return  latLng;
}
    public static void add(Poetry poetry,String columnName,String content){
        switch (columnName){
            case ContextInFirstLine.CITY_OF_ANCIENT:
                poetry.setCity_ancient(content);
                break;
            case ContextInFirstLine.CITY_OF_CURRENT:
                poetry.setCity_current(content);
                break;
            case ContextInFirstLine.COUNTY_OF_ANCIENT:
                poetry.setCounty_ancient(content);
                break;
            case ContextInFirstLine.COUNTY_OF_CURRENT:
                poetry.setCounty_current(content);
                break;
            case ContextInFirstLine.TITLE_OF_POETRY:
                poetry.setTitle(content);
                break;
            case ContextInFirstLine.CONTEXT_OF_POETRY:
                poetry.setContext(content);
                break;
            case ContextInFirstLine.LATTITUDE_OF_POETRY:
                try {
                    poetry.setLatitude(Double.parseDouble(content));
                }
                catch (java.lang.NumberFormatException e){
                    Log.e("POETRY","无法转化成浮点数"+content);
                }
                break;
            case ContextInFirstLine.LONGTITUDE_OF_POETRY:
                try {
                    poetry.setLongtitude(Double.parseDouble(content));

                }
                catch (java.lang.NumberFormatException e){
                    Log.e("POETRY","无法转化成浮点数"+content);
                }
                break;
            case ContextInFirstLine.AGE_OF_POET:
                poetry.setAgeOfPoet(Integer.parseInt(content));
                break;
        }
    }
    public Poet getPoet() {
        return poet;
    }
    public void setPoet(Poet poet) {
        this.poet = poet;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getCity_ancient() {
        return city_ancient;
    }

    public void setCity_ancient(String city_ancient) {
        this.city_ancient = city_ancient;
    }

    public String getCounty_ancient() {
        return county_ancient;
    }

    public void setCounty_ancient(String county_ancient) {
        this.county_ancient = county_ancient;
    }

    public String getCity_current() {
        return city_current;
    }

    public void setCity_current(String city_current) {
        this.city_current = city_current;
    }

    public String getCounty_current() {
        return county_current;
    }

    public void setCounty_current(String county_current) {
        this.county_current = county_current;
    }

    public int getAgeOfPoet() {
        return ageOfPoet;
    }

    public void setAgeOfPoet(int ageOfPoet) {
        this.ageOfPoet = ageOfPoet;
    }
}
