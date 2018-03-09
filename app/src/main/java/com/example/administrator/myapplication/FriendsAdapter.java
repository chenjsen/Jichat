package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class FriendsAdapter extends BaseAdapter {

    private LinkedList<Integer> data = null;
    private Context context = null;

    public FriendsAdapter(Context context,LinkedList<Integer> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_friends,viewGroup,false);
            holder = new ViewHolder();
            holder.txt_aName = view.findViewById(R.id.name);
            view.setTag(holder);   //将Holder存储到convertView中
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.txt_aName.setText(""+data.get(i));
        return view;
    }

    static class ViewHolder{
        TextView txt_aName;
    }
}
