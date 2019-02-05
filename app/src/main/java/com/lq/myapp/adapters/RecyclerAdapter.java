package com.lq.myapp.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.lq.myapp.R;
import com.lq.myapp.bean.VideoBean;
import com.lq.myapp.utils.LogUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.InnerHolder> {

    private String TAG = "RecyclerAdapter";
    private List<VideoBean> data = new ArrayList<>();
    private OnRecommendItemClickListener mRecommendItemClickListener = null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, "點擊了 ======>" + v.getTag());
                LogUtil.d(TAG, "position ======>" + position);
                if (mRecommendItemClickListener != null) {
                    mRecommendItemClickListener.onItemClick((Integer) v.getTag());
                }
            }
        });
        holder.setData(data.get(position));

    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public void setData(List<VideoBean> albumList) {
        if (albumList != null) {
            //this.data.clear();
            this.data.addAll(albumList);
        }
    }


    public class InnerHolder extends RecyclerView.ViewHolder{


        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setData(VideoBean data) {
            ImageView videoCover = itemView.findViewById(R.id.video_cover);
            TextView videoName = itemView.findViewById(R.id.video_name);

            videoName.setText(data.getTitle());
            Picasso.with(itemView.getContext())
                    .load(data.getCoverURL())
                    .into(videoCover);
        }
    }

    public void setOnRecommendItemClickListener(OnRecommendItemClickListener listener) {
        this.mRecommendItemClickListener = listener;
    }

    public interface OnRecommendItemClickListener {
        void onItemClick(int position);
    }


}
