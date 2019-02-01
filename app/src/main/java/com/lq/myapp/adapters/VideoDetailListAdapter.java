package com.lq.myapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lq.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailListAdapter extends BaseAdapter {

    private List<String> data = new ArrayList<>();
    private int count = 0;
    private Context mContext;

    public VideoDetailListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_listview, null);

        TextView textView = view.findViewById(R.id.jisu);
        textView.setText(data.get(position));


        return view;
    }

    public void setCount(int count) {
        this.count = count;
        for (int i = 1; i <= count; i++) {
            String s = "第" + i + "集";
            data.add(s);
        }

        //notifyDataSetChanged();
    }
}
