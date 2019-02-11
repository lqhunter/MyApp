package com.lq.myapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.myapp.R;
import com.lq.myapp.bean.PicMzituBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PicListAdapter extends RecyclerView.Adapter<PicListAdapter.InnerHolder> {

    private static final String TAG = "PicListAdapter";
    private List<PicMzituBean> data = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //找到view, 把view传给holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_item_view, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.itemView.setTag(position);

        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public void setData(List<PicMzituBean> data) {
        if (data != null) {
            this.data.addAll(data);
        }

    }

    public void setRefreshData(List<PicMzituBean> result) {
        if (data != null) {
            data.clear();
            this.data.addAll(result);
        }
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            ImageView picCover = itemView.findViewById(R.id.video_cover);
            TextView picTitle = itemView.findViewById(R.id.video_name);

            picTitle.setText(data.get(position).getTitle());
            Picasso.with(itemView.getContext())
                    .load(data.get(position).getThumb_src_min())
                    .placeholder(R.mipmap.ic_default_view_vertical)//图片没加载完成时的默认图片
                    .into(picCover);
        }
    }
}
