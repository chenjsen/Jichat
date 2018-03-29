package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<ChatEntity> list;

    public ChatAdapter(Context context, List<ChatEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatAdapter.ViewHolder holder = null;
        ChatEntity ce = list.get(i);
        if(view == null){
            if(ce.isLeft()){
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_left,viewGroup,false);
                holder = new ChatAdapter.ViewHolder();
                holder.tv_account = view.findViewById(R.id.account_chat_left);
                holder.tv_content = view.findViewById(R.id.message_chat_left);
                view.setTag(holder);   //将Holder存储到convertView中
            }else{
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_right,viewGroup,false);
                holder = new ChatAdapter.ViewHolder();
                holder.tv_account = view.findViewById(R.id.account_chat_right);
                holder.tv_content = view.findViewById(R.id.message_chat_right);
                view.setTag(holder);   //将Holder存储到convertView中
            }

        }else{
            holder = (ChatAdapter.ViewHolder) view.getTag();
        }
        holder.tv_account.setText(""+ce.getAccount());
        holder.tv_content.setText(""+ce.getContent());
        return view;
    }

    static class ViewHolder{
        TextView tv_account;
        TextView tv_content;
    }
}
