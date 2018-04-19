package com.example.LJJ.MyUser;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class User implements Parcelable{
    private String nickname;
    private String account;
    private String password;
    private boolean sex;
    private List<String> collect;
    private List<String> friends;
    private String id;

    public User(){
        nickname="某人";
        collect=new ArrayList<String>();
        friends=new ArrayList<String>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getCollect() {
        return collect;
    }

    public void setCollect(List<String> collect) {
        this.collect = collect;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public static final Creator<User> CREATOR=new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            User bean=new User();

            bean.account=source.readString();
            bean.password=source.readString();
            bean.sex=source.readByte()!=0;
            bean.friends=source.readArrayList(String.class.getClassLoader());
            bean.collect=source.readArrayList(String.class.getClassLoader());
            bean.id= source.readString();
            bean.nickname=source.readString();
            return bean;
        }

        @Override
        public User[] newArray(int size) {
            // TODO Auto-generated method stub
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(account);
        dest.writeString(password);
        dest.writeByte((byte)(sex?1:0));
        dest.writeStringList(friends);
        dest.writeStringList(collect);
        dest.writeString(nickname);
        dest.writeString(id);
    }
}
