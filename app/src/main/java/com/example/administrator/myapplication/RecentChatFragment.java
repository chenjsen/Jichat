package com.example.administrator.myapplication;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/5/7 0007.
 */

public class RecentChatFragment extends Fragment {

    LocalBroadcastManager lbm;
    ListView listView;
    List<RecentEntity> chatEntityList=new ArrayList<RecentEntity>();
    String[] mes;
    MsgBroadcastReceiver br;
    RecentAdapter recentAdapter;
    ChatLogDBOpenHelper chatLogDBOpenHelper;
    Integer userAccount;
    SQLiteDatabase db;

    public RecentChatFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_chat_layout,container,false);

        //注册广播
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("com.example.administrator.mes");
        lbm = LocalBroadcastManager.getInstance(getActivity());
        br=new MsgBroadcastReceiver();
        lbm.registerReceiver(br, myIntentFilter);
        chatLogDBOpenHelper = new ChatLogDBOpenHelper(getActivity(),"chat_log.db",null,1);
        db = chatLogDBOpenHelper.getReadableDatabase();

        chatEntityList.clear();
        getData(111);
        getData(222);
        getData(333);
        listView =  view.findViewById(R.id.lv_recent);
        recentAdapter = new RecentAdapter(this.getActivity(), chatEntityList);
        listView.setAdapter(recentAdapter);
        userAccount = getActivity().getIntent().getIntExtra("username",0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                int account=Integer.parseInt(mes[0]);
                chatEntityList.get(position).setRead(true);
                recentAdapter.notifyDataSetChanged();
                //打开聊天页面
                Intent intent=new Intent(getActivity(),ChatActivity.class);
                intent.putExtra("account", account);
                intent.putExtra("userAccount", userAccount);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        lbm.unregisterReceiver(br);
        super.onDestroy();
    }

    //广播接收器
    public class MsgBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mes = intent.getStringArrayExtra("message");
            Log.e("Jichat78", "78"+mes.toString());
            //更新最近会话列表， 检测chatEntityList，防止同一个好友的消息出现多个会话实体
            Iterator it = chatEntityList.iterator();
            if (chatEntityList != null && chatEntityList.size() != 0) {
                while (it.hasNext()) {
                    RecentEntity re = (RecentEntity) it.next();
                    if (re.getAccount() == Integer.parseInt(mes[0])) {
                        chatEntityList.remove(re);
                    }
                }
            }
            chatEntityList.add(new RecentEntity(Integer.parseInt(mes[0]), mes[1], false));
            recentAdapter.notifyDataSetChanged();
            Toast.makeText(context, mes[0] + "： " + mes[1], Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = chatLogDBOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("userAccount",userAccount+"");
            values.put("account",mes[0]);
            values.put("content",mes[1]);
            values.put("isLeft",0);
            //db.insert("chat_log"+mes[0],null,values);
            db.close();
        }
    }

    public void getData(Integer i) {
        if(i == userAccount )
        {
            return;
        }
        Cursor cursor = db.query("chat_log"+i, null, null, null, null, null, null);
        if(cursor.moveToLast()){
            int account = Integer.parseInt(cursor.getString(cursor.getColumnIndex("account")));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            RecentEntity recentEntity = new RecentEntity(account,content,true);
            chatEntityList.add(recentEntity);
        }
        cursor.close();
    }

}
