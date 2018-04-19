package com.example.sf.DataBase;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sf on 17-12-7.
 * 存放诗人的信息的表，仅包含诗人的基本信息，如姓名，朝代等
 * 不包含具体的诗词信息
 * 诗人与其诗词信息采用一对多的结构分别在两张表中存储
 */

public class Poet extends DataSupport{
    /**
     * excel表中第一行中的题头的命名标准，用于在读取表的时候确定相应的列名在第几列*/
    public static final ArrayList<String> FirstLine= new ArrayList<String>(
            Arrays.asList(ContextInFirstLine.NAME_OF_PORT,ContextInFirstLine.SEX_OF_PORT,ContextInFirstLine.DYNASTY_OF_PORT,"出生日期","死亡日期")
    );
    public static int idFlag=0;
    private String name;
    private String Sex;
    private String Dynasty;
    private String DayOfBirth;
    private String DayOfDeath;
    private List<Poetry> poetries=new ArrayList<>();

    public static ArrayList<String> getFirstLine() {
        return FirstLine;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Poet(){
    }
    public static void add(Poet poet,String columnName,String content){
        switch (columnName){
            case ContextInFirstLine.NAME_OF_PORT:
                poet.setName(content);
                break;
            case ContextInFirstLine.DYNASTY_OF_PORT:
                poet.setDynasty(content);
                break;
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getDynasty() {
        return Dynasty;
    }

    public void setDynasty(String dynasty) {
        Dynasty = dynasty;
    }

    public String getDayOfBirth() {
        return DayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        DayOfBirth = dayOfBirth;
    }

    public String getDayOfDeath() {
        return DayOfDeath;
    }

    public void setDayOfDeath(String dayOfDeath) {
        DayOfDeath = dayOfDeath;
    }

    public List<Poetry> getPoetries() {
        return poetries;
    }

    public void setPoetries(List<Poetry> poetries) {
        this.poetries = poetries;
    }


}
