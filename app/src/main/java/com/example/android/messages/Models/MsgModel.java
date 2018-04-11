package com.example.android.messages.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pc on 4/6/2018.
 */

public class MsgModel implements Serializable {
    String userPhone;
    String messageTxt;
    Date sendAt;

    public MsgModel() {
    }

    public MsgModel(String userPhone, String messageTxt, Date sendAt) {
        this.userPhone = userPhone;
        this.messageTxt = messageTxt;
        this.sendAt = sendAt;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getMessageTxt() {
        return messageTxt;
    }

    public Date getSendAt() {
        return sendAt;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setMessageTxt(String messageTxt) {
        this.messageTxt = messageTxt;
    }

    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }
}
