package com.prouman.model;

/**
 * Created by jcs on 12/13/2016.
 */
public class MessageModel {
    String title,msgText;

    public MessageModel(String title, String msgText) {
        this.title = title;
        this.msgText = msgText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
}
