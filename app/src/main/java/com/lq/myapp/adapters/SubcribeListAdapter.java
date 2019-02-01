package com.lq.myapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.myapp.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Created by lqhunter on 2019/1/5.
 */

public class SubcribeListAdapter extends RecyclerView.Adapter<SubcribeListAdapter.InnerHolder>{

    List<Album> albums = new ArrayList<>();

    @NonNull
    @Override
    public SubcribeListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_recommend_item, parent, false);


        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcribeListAdapter.InnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(albums.get(position));
    }

    @Override
    public int getItemCount() {
        if (albums != null) {
            return albums.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setData(Album album) {

        }
    }
}
