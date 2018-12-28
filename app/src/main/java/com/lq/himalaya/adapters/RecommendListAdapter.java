package com.lq.himalaya.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.himalaya.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by lqhunter on 2018/12/27.
 */

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder>{

    private List<Album> mData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //找到view,把view传给holder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item, parent, false);

        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        //通过 holder 把数据和 view 绑定
        holder.itemView.setTag(position);
        holder.setData(mData.get(position));

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if (albumList != null) {
            mData.clear();
            mData.addAll(albumList);
        }
        //更新UI
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            //找到各个控件，设置数据
            //专辑封面
            ImageView albumCoverTv = itemView.findViewById(R.id.album_cover);
            //专辑标题
            TextView albumTitleTv = itemView.findViewById(R.id.album_title_tv);
            //专辑描述
            TextView albumDesTv = itemView.findViewById(R.id.album_description_tv);
            //播放数
            TextView albumPlayCountTv = itemView.findViewById(R.id.album_play_count);
            //总内容数量
            TextView albumContentCountTv = itemView.findViewById(R.id.album_content_size);

            albumTitleTv.setText(album.getAlbumTitle());
            albumDesTv.setText(album.getAlbumIntro());
            albumPlayCountTv.setText(album.getPlayCount() + "");
            albumContentCountTv.setText(album.getIncludeTrackCount() + "");

            Picasso.with(itemView.getContext())
                    .load(album.getCoverUrlLarge())
                    .into(albumCoverTv);
        }
    }
}
