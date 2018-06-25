package com.example.administrator.myapplication;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    ChatLogDBOpenHelper chatLogDBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindView();
        userAccount = getIntent().getIntExtra("userAccount",0);
        chatAccount = getIntent().getIntExtra("account",0);
        tv_title.setText("chat with " + chatAccount);
        lbm = LocalBroadcastManager.getInstance(this);
        chatLogDBOpenHelper = new ChatLogDBOpenHelper(this,"chat_log.db",null,1);
        SQLiteDatabase db = chatLogDBOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("chat_log"+chatAccount, null, "userAccount = " + userAccount, null, null, null, null);
        ChatData.clear();
        Intent intent = new Intent();
        intent.setAction("com.jison.example.service");
        intent.setPackage(this.getPackageName());
        stopService(intent);
        if(cursor.moveToFirst()){
            do{
                int account = Integer.parseInt(cursor.getString(cursor.getColumnIndex("account")));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Boolean isLeft = (cursor.getInt(cursor.getColumnIndex("isLeft")) == 0) ?true:false;
                ChatEntity chatEntity = new ChatEntity(account,content,isLeft);
                ChatData.add(chatEntity);
            }while(cursor.moveToNext());
            lv_chat.setAdapter(new ChatAdapter(this,ChatData));
        }
        cursor.close();
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
        Intent intent = new Intent();
        intent.setAction("com.jison.example.service");
        intent.setPackage(this.getPackageName());
        startService(intent);
        super.onDestroy();
    }

    void bindView(){
        tv_title = findViewById(R.id.tv_title);
        et_chat = findViewById(R.id.et_chat);
        bt_send = findViewById(R.id.bt_send);
        lv_chat = findViewById(R.id.lv_chat);
    }

    public void updateChatView(ChatEntity chatEntity){
        ChatData.add(chatEntity);
        lv_chat.setAdapter(new ChatAdapter(this,ChatData));
        SQLiteDatabase db = chatLogDBOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userAccount",userAccount+"");
        values.put("account",chatEntity.getAccount()+"");
        values.put("content",chatEntity.getContent());
        values.put("isLeft",(chatEntity.isLeft() ? 0 :1));
        db.insert("chat_log"+chatAccount,null,values);
        db.close();
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
        }
    }
}
