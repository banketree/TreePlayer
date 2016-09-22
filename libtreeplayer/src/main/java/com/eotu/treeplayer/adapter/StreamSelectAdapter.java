package com.eotu.treeplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.eotu.treeplayer.bean.VideoijkBean;
import com.eotu.treeplayer.utils.ResourceUtils;

import java.util.List;


public class StreamSelectAdapter extends BaseAdapter {

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 布局填充对象
     */
    private LayoutInflater layoutInflater;
    /**
     * 不同分辨率播放地址集合
     */
    private List<VideoijkBean> listVideos;

    public StreamSelectAdapter(Context context, List<VideoijkBean> list) {
        this.mContext = context;
        this.listVideos = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return listVideos.size();
    }

    public Object getItem(int position) {
        return listVideos.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "player_list_item"), (ViewGroup) null);
            holder = new ViewHolder();
            holder.streamName = (TextView) convertView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "player_stream_name"));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VideoijkBean mVideoijkBean = listVideos.get(position);
        String streamName = mVideoijkBean.getStream();
        holder.streamName.setText(streamName);
        if (mVideoijkBean.isSelect()) {
            holder.streamName.setTextColor(mContext.getResources().getColor(ResourceUtils.getResourceIdByName(mContext, "color", "player_stream_name_playing")));
        } else {
            holder.streamName.setTextColor(mContext.getResources().getColor(ResourceUtils.getResourceIdByName(mContext, "color", "player_stream_name_normal")));
        }
        return convertView;
    }

    class ViewHolder {
        public TextView streamName;

        ViewHolder() {
        }
    }
}