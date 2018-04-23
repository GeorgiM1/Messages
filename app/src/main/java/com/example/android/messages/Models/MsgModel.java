package com.example.android.messages.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pc on 4/6/2018.
 */

public class MsgModel implements Serializable {
    String notice_ID;
    Date date;
    int time;
    String Phone_ID;
    String notice_text;



    public MsgModel() {
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getNotice_ID() {
        return notice_ID;
    }

    public void setNotice_ID(String notice_ID) {
        this.notice_ID = notice_ID;
    }

    public MsgModel(String Phone_ID, String notice_text, Date date) {
        this.Phone_ID = Phone_ID;
        this.notice_text = notice_text;
        this.date = date;
    }

    public String getPhone_ID() {
        return Phone_ID;
    }

    public String getNotice_text() {
        return notice_text;
    }

    public Date getDate() {
        return date;
    }

    public void setPhone_ID(String phone_ID) {
        this.Phone_ID = phone_ID;
    }

    public void setNotice_text(String notice_text) {
        this.notice_text = notice_text;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
