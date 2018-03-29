package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class ChatEntity {
    private int account;
    private String content;
    private boolean isLeft;

    public ChatEntity(int account, String content, boolean isLeft) {
        this.account = account;
        this.content = content;
        this.isLeft = isLeft;
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

    public void setLeft(boolean left) {
        isLeft = left;
    }
}
