package com.example.administrator.myapplication;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class JiChatMsg implements Serializable {

    public String type;
    public int sender;
    public int receiver;
    public String content;
    public void setContent(String content) {
        this.content = content;
    }
    public String sendTime;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getSender() {
        return sender;
    }
    public void setSender(int sender) {
        this.sender = sender;
    }
    public int getReceiver() {
        return receiver;
    }
    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }
    public String getContent() {
        return content;
    }

}
