package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class ClientConServerThread extends Thread {

    private Context context;
    private Socket s;
    Handler handler;
    public Socket getS() {return s;}
    public ClientConServerThread(Context context, Socket s, Handler handler){
        this.context=context;
        this.s=s;
        this.handler = handler;
    }

    @Override
    public void run() {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        while(true){
            ObjectInputStream ois = null;
            JiChatMsg jcm;
            try {
                ois = new ObjectInputStream(s.getInputStream());
                jcm=(JiChatMsg) ois.readObject();
                if(jcm.getType().equals(JiChatMsgType.COM_MES)
                        || jcm.getType().equals(JiChatMsgType.GROUP_MES)){//如果是聊天消息
                    //把从服务器获得的消息通过广播发送
                    Intent intent = new Intent("com.example.administrator.mes");
                    String[] message=new String[]{
                            jcm.getSender()+"",
                            jcm.getContent(),};
                    Log.e("Jichat2", "44"+message.toString());
                    intent.putExtra("message", message);
                    lbm.sendBroadcast(intent);
                }else if(jcm.getType().equals(JiChatMsgType.RET_ONLINE_FRIENDS)){//如果是好友列表
                    //更新好友，群
                    String s[] = jcm.getContent().split(" ");
                    JichatMainActivity.data.clear();
                    for(String temp :s){
                        Log.e("Jichat",temp);
                        JichatMainActivity.data.add(Integer.parseInt(temp));
                    }
                    handler.sendEmptyMessage(0x123);
                    //Log.i("", "--"+s[0]);
                    //Log.i("", "--"+s[1]);
                }
//                if(jcm.getType().equals(JiChatMsgType.SUCCESS)){
//                    Toast.makeText(context, "操作成功！", Toast.LENGTH_SHORT);
//                }
            } catch (Exception e) {
                //e.printStackTrace();
                try {
                    if(s!=null){
                        s.close();
                    }
                } catch (IOException e1) {
                    //e1.printStackTrace();
                }
            }
        }
    }


}
