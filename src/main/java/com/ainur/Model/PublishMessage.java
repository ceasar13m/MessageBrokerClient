package com.ainur.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishMessage {
    private String token;
    private String channelName;
    private String message;
    private Date sendDate;


    public Date getDate() {
        return sendDate;
    }

    public void setDate() {
        Date sendDate = new Date();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
