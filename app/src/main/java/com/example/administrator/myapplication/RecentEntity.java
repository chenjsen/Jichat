package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

class RecentEntity {
    private int account;
    private String content;
    private boolean isRead;

    public RecentEntity(int account,String content,boolean isRead){
        this.account=account;
        this.content=content;
        this.isRead=isRead;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
