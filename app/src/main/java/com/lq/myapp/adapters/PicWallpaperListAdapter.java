package com.lq.myapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lq.myapp.R;
import com.lq.myapp.bean.PicWallpaperBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PicWallpaperListAdapter extends RecyclerView.Adapter<PicWallpaperListAdapter.InnerHolder> {

    private List<PicWallpaperBean.ResBean.VerticalBean> data = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_item_wallpaper_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(position);
    }

    public void setData(List<PicWallpaperBean.ResBean.VerticalBean> data) {
        if (data != null) {
            this.data.addAll(data);
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            ImageView imageView = itemView.findViewById(R.id.pic_item_wallpaper_view);
            Picasso.with(imageView.getContext()).load(data.get(position).getThumb())
                    .placeholder(R.mipmap.ic_default_view_vertical)//图片没加载完成时的默认图片
                    .into(imageView);
        }
    }
}
