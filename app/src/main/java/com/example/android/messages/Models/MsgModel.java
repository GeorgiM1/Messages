package com.example.android.messages.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pc on 4/6/2018.
 */

public class MsgModel implements Serializable {
    String phone_id;
    String notice_text;
    Date date;
    int time;
    String notice_ID;

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

    public MsgModel(String phone_id, String notice_text, Date date) {
        this.phone_id = phone_id;
        this.notice_text = notice_text;
        this.date = date;
    }

    public String getPhone_id() {
        return phone_id;
    }

    public String getNotice_text() {
        return notice_text;
    }

    public Date getDate() {
        return date;
    }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }

    public void setNotice_text(String notice_text) {
        this.notice_text = notice_text;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
