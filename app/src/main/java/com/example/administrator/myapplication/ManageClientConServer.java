package com.example.administrator.myapplication;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class ManageClientConServer {
    private static HashMap hm=new HashMap<Integer,ClientConServerThread>();
    //把创建好的ClientConServerThread放入到hm
    public static void addClientConServerThread(int account,ClientConServerThread ccst){
        hm.put(account, ccst);
    }

    //可以通过account取得该线程
    public static ClientConServerThread getClientConServerThread(int i){
        return (ClientConServerThread)hm.get(i);
    }
}
