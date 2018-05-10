package com.example.administrator.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    TextView tv_title;
    EditText et_chat;
    Button   bt_send;
    ListView lv_chat;
    String   content;
    int      userAccount;
    int      chatAccount;
    public static List<ChatEntity> ChatData = new ArrayList<ChatEntity>() ;
    MyBroadcastReceiver myBroadcastReceiver;
    LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindView();
        userAccount = getIntent().getIntExtra("userAccount",0);
        chatAccount = getIntent().getIntExtra("account",0);
        tv_title.setText("chat with " + chatAccount);
        lbm = LocalBroadcastManager.getInstance(this);


        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = et_chat.getText().toString();
                et_chat.setText("");
                updateChatView(new ChatEntity(userAccount,content,false));

                //向服务端发送数据
                try{
                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServer.getClientConServerThread(userAccount).getS().getOutputStream());
                    JiChatMsg jcm = new JiChatMsg();
                    jcm.setType(JiChatMsgType.COM_MES);
                    jcm.setReceiver(chatAccount);
                    jcm.setSender(userAccount);
                    jcm.setContent(content);
                    oos.writeObject(jcm);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.administrator.mes");
        myBroadcastReceiver = new MyBroadcastReceiver();
        lbm.registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy(){
        lbm.unregisterReceiver(myBroadcastReceiver);
        super.onDestroy();
    }

    void bindView(){
        tv_title = findViewById(R.id.tv_title);
        et_chat = findViewById(R.id.et_chat);
        bt_send = findViewById(R.id.bt_send);
        lv_chat = findViewById(R.id.lv_chat);
    }

    void updateChatView(ChatEntity chatEntity){
        ChatData.add(chatEntity);
        lv_chat.setAdapter(new ChatAdapter(this,ChatData));
    }

    //广播接收器
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String[] mes = intent.getStringArrayExtra("message");
            //更新聊天内容
            Toast.makeText(context, "收到["+mes[0]+"]的消息："+mes[1], Toast.LENGTH_SHORT).show();
            Log.e("Jichat2", "94"+mes.toString());
            //abortBroadcast();
            updateChatView(new ChatEntity(Integer.parseInt(mes[0]),mes[1],true));
            Intent it = new Intent(context, ChatActivity.class);
            PendingIntent pit = PendingIntent.getActivity(context, 0, it, 0);
        }
    }
}
