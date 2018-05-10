package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

class RecentAdapter extends BaseAdapter {
    private Context context;
    private List<RecentEntity> list;
    LayoutInflater inflater;

    public RecentAdapter(Context context,List<RecentEntity> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup root) {
        RecentAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.recent_listview_item, root,false);
            holder = new RecentAdapter.ViewHolder();
            holder.isRead = convertView.findViewById(R.id.iv_tip_mes_r);
            holder.tv_account = convertView.findViewById(R.id.tv_Account);
            holder.tv_content = convertView.findViewById(R.id.tv_chat_content_r);
            convertView.setTag(holder);
        } else {
            holder = (RecentAdapter.ViewHolder) convertView.getTag();
        }

        RecentEntity re=list.get(position);
        holder.tv_content.setText(re.getContent());
        if(re.isRead()){
            holder.isRead.setVisibility(View.GONE);
        }else{
            holder.isRead.setImageResource(R.mipmap.bg_num);
        }
        holder.tv_account.setText(""+re.getAccount());
        return convertView;
    }
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        TextView tv_account;
        TextView tv_content;
        ImageView isRead;
    }
}
