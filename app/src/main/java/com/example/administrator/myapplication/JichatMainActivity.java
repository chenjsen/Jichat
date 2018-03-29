package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

public class JichatMainActivity extends AppCompatActivity {
    private ListView lv;
    public static LinkedList<Integer> data = new LinkedList<>();
    private Context context = null;
    private FriendsAdapter friendsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //data.add(new Integer(111));
        //data.add(new Integer(222));
        //data.add(new Integer(333));
        setContentView(R.layout.activity_jichat_main);
        context = JichatMainActivity.this;
        lv = findViewById(R.id.lv_friend);
        friendsAdapter = new FriendsAdapter(context,data);
        lv.setAdapter(friendsAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Integer userAccount = getIntent().getIntExtra("username",0);
                Integer account = (Integer) lv.getItemAtPosition(i);
                Intent intent = new Intent(JichatMainActivity.this,ChatActivity.class);

                Log.e("Jichat1",account+"");
                Log.e("Jichat1",userAccount+"");
                intent.putExtra("account",account);
                intent.putExtra("userAccount",userAccount);
                startActivity(intent);
            }
        });
    }
}
