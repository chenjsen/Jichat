package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class ChatEntity {
    private int account;
    private String content;//如果是语音，这里保存的是文件的地址，否则就是文本信息
    private boolean isLeft;
    private boolean isAudio;//用来判断是否是语音
    private float time;//语音的时间


    public ChatEntity(int account, String content, boolean isLeft,boolean isAudio,float time) {
        this.account = account;
        this.content = content;
        this.isLeft  = isLeft;
        this.isAudio = isAudio;
        this.time    = time;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public boolean isAudio(){
        return isAudio;
    }

    public float getTime(){
        return time;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }
}
