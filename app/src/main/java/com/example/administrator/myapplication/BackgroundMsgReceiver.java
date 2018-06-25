package com.example.administrator.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class BackgroundMsgReceiver extends BroadcastReceiver {

    private Notification notify1;
    private NotificationManager mNManager;
    private ChatLogDBOpenHelper chatLogDBOpenHelper;

    public BackgroundMsgReceiver(ChatLogDBOpenHelper chatLogDBOpenHelper) {
        this.chatLogDBOpenHelper = chatLogDBOpenHelper;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String[] mes = intent.getStringArrayExtra("message");
        Log.e("account", "account:"+mes[0]+"------------------------   userAccount" + mes[2]);
        Intent it = new Intent(context, ChatActivity.class);
        it.putExtra("account",Integer.parseInt(mes[0]));
        it.putExtra("userAccount",Integer.parseInt(mes[2]));
        it.setAction(""+System.currentTimeMillis());
        PendingIntent pit = PendingIntent.getActivity(context, 0, it, 0);

        SQLiteDatabase db = chatLogDBOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userAccount",mes[2]+"");
        values.put("account",mes[0]+"");
        values.put("content",mes[1]+"");
        values.put("isLeft",0);
        db.insert("chat_log"+mes[0],null,values);
        db.close();

        mNManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        mBuilder.setContentTitle(mes[0]);
        mBuilder.setContentText(mes[1]);
        mBuilder.setTicker("收到"+mes[0]+"发过来的信息");
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pit);
        notify1 = mBuilder.build();

        //abortBroadcast();
        mNManager.notify(1,notify1);
    }
}
