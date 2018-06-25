package com.example.administrator.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public class RcvMsgService extends Service {
    public static BackgroundMsgReceiver receiver;
    LocalBroadcastManager lbm;
    ChatLogDBOpenHelper chatLogDBOpenHelper;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        chatLogDBOpenHelper = new ChatLogDBOpenHelper(this,"chat_log.db",null,1);
        super.onCreate();
        lbm = LocalBroadcastManager.getInstance(this);
        receiver = new BackgroundMsgReceiver(chatLogDBOpenHelper);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.administrator.mes");
        lbm.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        lbm.unregisterReceiver(receiver);
    }
}
