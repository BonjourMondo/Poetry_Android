package com.example.leesanghyuk.POJO;


import java.sql.Timestamp;

/**
 * Created by LeesangHyuk on 2018/3/20.
 */

public class CommentInfo {
    private int user_id;
    private Timestamp date;
    private String comment;
    private int poet_id;

    public CommentInfo() {
    }

    public CommentInfo(int user_id, Timestamp date, String comment, int poet_id) {
        this.user_id = user_id;
        this.date = date;
        this.comment = comment;
        this.poet_id = poet_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPoet_id() {
        return poet_id;
    }

    public void setPoet_id(int poet_id) {
        this.poet_id = poet_id;
    }
}
