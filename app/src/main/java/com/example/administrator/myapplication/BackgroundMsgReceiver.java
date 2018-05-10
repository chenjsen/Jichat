package com.example.administrator.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class BackgroundMsgReceiver extends BroadcastReceiver {

    private Notification notify1;
    private NotificationManager mNManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        String[] mes = intent.getStringArrayExtra("message");
        Log.e("Jichat3", "receive the message");
        Intent it = new Intent(context, ChatActivity.class);
        PendingIntent pit = PendingIntent.getActivity(context, 0, it, 0);

        //ChatActivity.ChatData.add((new ChatEntity(Integer.parseInt(mes[0]),mes[1],true)));


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
