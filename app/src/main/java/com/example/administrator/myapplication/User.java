package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class User {
    public int acount;
    public String password;

    public User(int a,String p) {
        this.acount = a;
        this.password = p;
    }

    public int getAcount() {
        return acount;
    }

}
