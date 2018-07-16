package com.example.administrator.myapplication;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<ChatEntity> list;
    //item 最小最大值
    private int mMinItemWidth;
    private int mMaxIItemWidth;

    public ChatAdapter(Context context, List<ChatEntity> list) {
        this.context = context;
        this.list = list;

        //获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        //item 设定最小最大值
        mMaxIItemWidth = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);
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
//        if(view == null){
            if(ce.isAudio()){
                if(ce.isLeft()){
                    view = LayoutInflater.from(context).inflate(R.layout.item_audio_left,viewGroup,false);
                    holder = new ChatAdapter.ViewHolder();
                    holder.tv_account = view.findViewById(R.id.account_audio_left);
                }else{
                    view = LayoutInflater.from(context).inflate(R.layout.item_audio_right,viewGroup,false);
                    holder = new ChatAdapter.ViewHolder();
                    holder.tv_account = view.findViewById(R.id.account_chat_right);
                }
                holder.tv_time = view.findViewById(R.id.id_recorder_time);
                holder.length = view.findViewById(R.id.id_recorder_length);
                //view.setTag(holder);   //将Holder存储到convertView中
            } else {
                if(ce.isLeft()){
                    view = LayoutInflater.from(context).inflate(R.layout.item_chat_left,viewGroup,false);
                    holder = new ChatAdapter.ViewHolder();
                    holder.tv_account = view.findViewById(R.id.account_chat_left);
                    holder.tv_content = view.findViewById(R.id.message_chat_left);
                }else{
                    view = LayoutInflater.from(context).inflate(R.layout.item_chat_right,viewGroup,false);
                    holder = new ChatAdapter.ViewHolder();
                    holder.tv_account = view.findViewById(R.id.account_chat_right);
                    holder.tv_content = view.findViewById(R.id.message_chat_right);
                }
                //view.setTag(holder);   //将Holder存储到convertView中
            }


//        }else{
//            holder = (ChatAdapter.ViewHolder) view.getTag();
//        }

        if(ce.isAudio()){
            holder.tv_account.setText(""+ce.getAccount());
            holder.tv_time.setText(""+Math.round(ce.getTime())+"\"");
            ViewGroup.LayoutParams lp = holder.length.getLayoutParams();
            lp.width = (int) (mMinItemWidth + (mMaxIItemWidth / 60f*ce.getTime()));
        } else{
            holder.tv_account.setText(""+ce.getAccount());
            holder.tv_content.setText(""+ce.getContent());
        }

        return view;
    }

    static class ViewHolder{
        TextView tv_account;
        TextView tv_content;
        TextView tv_time;
        View     length;
    }
}
