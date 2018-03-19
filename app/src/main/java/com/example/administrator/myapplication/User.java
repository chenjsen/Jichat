package com.example.administrator.myapplication;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class User implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public int account;
    public String password;
    String operation;

    public User(int a,String p) {
        this.account = a;
        this.password = p;
    }

    public int getAccount() {
        return account;
    }


    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setAccount(int account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
