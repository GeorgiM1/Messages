package com.example.android.messages.Models;

/**
 * Created by pc on 4/6/2018.
 */

public class MsgModel {
    String user;
    String messageTxt;
    String sentAt;

    public MsgModel(String user, String messageTxt, String sentAt) {
        this.user = user;
        this.messageTxt = messageTxt;
        this.sentAt = sentAt;
    }

    public String getUser() {
        return user;
    }

    public String getMessageTxt() {
        return messageTxt;
    }

    public String getSentAt() {
        return sentAt;
    }
}
