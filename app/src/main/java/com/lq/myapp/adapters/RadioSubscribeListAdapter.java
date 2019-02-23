package com.lq.myapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.myapp.R;
import com.lq.myapp.utils.LogUtil;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lqhunter
 * date : 2019/2/23 0023
 * description :
 */
public class RadioSubscribeListAdapter extends RecyclerView.Adapter<RadioSubscribeListAdapter.InnerHolder> {

    private static final String TAG = "RadioSubscribeListAdapter";
    private List<Album> data = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribe_list_view, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, "点击了。。。" + position);
            }
        });
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        if (data.size() != 0) {
            return data.size();
        }
        return 0;
    }

    public void setData(List<Album> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            Album indexData = data.get(position);
            ImageView cover = itemView.findViewById(R.id.album_cover);
            TextView title = itemView.findViewById(R.id.album_title_tv);
            TextView description = itemView.findViewById(R.id.album_description_tv);
            TextView updata = itemView.findViewById(R.id.latest_update);

            title.setText(indexData.getAlbumTitle());
            description.setText(indexData.getAlbumIntro());
            updata.setText(indexData.getUpdatedAt() + "");

            Picasso.with(itemView.getContext()).load(indexData.getCoverUrlMiddle()).into(cover);

        }
    }
}
