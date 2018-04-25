package com.example.administrator.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class MyFragment extends Fragment{

    private ListView lv;
    private FriendsAdapter friendsAdapter = null;

    public MyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_my,container,false);
        lv = view.findViewById(R.id.lv_friend);
        friendsAdapter = new FriendsAdapter(this.getActivity(),JichatMainActivity.data);
        lv.setAdapter(friendsAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("Jichat1","click item performed");
                Integer userAccount = getActivity().getIntent().getIntExtra("username",0);
                Integer account = (Integer) lv.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(),ChatActivity.class);

                Log.e("Jichat1",account+"");
                Log.e("Jichat1",userAccount+"");
                intent.putExtra("account",account);
                intent.putExtra("userAccount",userAccount);
                startActivity(intent);
            }
        });
        return view;
    }
}