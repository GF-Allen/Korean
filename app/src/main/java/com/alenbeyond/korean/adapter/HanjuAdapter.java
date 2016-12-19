package com.alenbeyond.korean.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alenbeyond.korean.R;
import com.alenbeyond.korean.bean.Hanju;

import java.util.List;

/**
 * Created by alen on 16/12/19.
 */

public class HanjuAdapter extends BaseAdapter {

    private List<Hanju> data;
    private Context context;

    public HanjuAdapter(List<Hanju> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.textView.setText(data.get(position).getEpisodes());

        return convertView;
    }

    static class Holder {
        public TextView textView;
    }

}
