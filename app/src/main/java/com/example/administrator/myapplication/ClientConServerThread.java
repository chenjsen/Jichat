package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
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
    public Socket getS() {return s;}
    public ClientConServerThread(Context context,Socket s){
        this.context=context;
        this.s=s;
    }

    @Override
    public void run() {
        while(true){
            ObjectInputStream ois = null;
            JiChatMsg jcm;
            try {
                ois = new ObjectInputStream(s.getInputStream());
                jcm=(JiChatMsg) ois.readObject();
                if(jcm.getType().equals(JiChatMsgType.COM_MES)
                        || jcm.getType().equals(JiChatMsgType.GROUP_MES)){//如果是聊天消息
                    //把从服务器获得的消息通过广播发送
                    Intent intent = new Intent("org.yhn.yq.mes");
                    String[] message=new String[]{};
                    Log.i("--", message.toString());
                    intent.putExtra("message", message);
                    context.sendBroadcast(intent);
                }else if(jcm.getType().equals(JiChatMsgType.RET_ONLINE_FRIENDS)){//如果是好友列表
                    //更新好友，群
                    String s[] = jcm.getContent().split(" ");
                    for(String temp :s){
                        Log.e("Jichat",temp);
                        JichatMainActivity.data.add(Integer.parseInt(temp));
                    }

                    //Log.i("", "--"+s[0]);
                    //Log.i("", "--"+s[1]);
                }
                if(jcm.getType().equals(JiChatMsgType.SUCCESS)){
                    Toast.makeText(context, "操作成功！", Toast.LENGTH_SHORT);
                }
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
