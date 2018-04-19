package com.example.leesanghyuk.POJO;

/**
 * Created by LeesangHyuk on 2018/3/12.
 */

public class PoetInfo {
    //诗信息的POJO
    private String author;//诗人的名字
    private String content;//诗的内容
    private String title;//诗的题目
    private String info;
    private String id;

    public PoetInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
