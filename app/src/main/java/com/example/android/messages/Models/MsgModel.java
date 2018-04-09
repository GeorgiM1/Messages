package com.example.android.messages.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pc on 4/6/2018.
 */

public class MsgModel implements Serializable {
    String user;
    String messageTxt;
    Date sendAt;

    public MsgModel() {
    }

    public MsgModel(String user, String messageTxt, Date sendAt) {
        this.user = user;
        this.messageTxt = messageTxt;
        this.sendAt = sendAt;
    }

    public String getUser() {
        return user;
    }

    public String getMessageTxt() {
        return messageTxt;
    }

    public Date getSendAt() {
        return sendAt;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMessageTxt(String messageTxt) {
        this.messageTxt = messageTxt;
    }

    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }
}
