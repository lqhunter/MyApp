package com.lq.myapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.myapp.R;
import com.lq.myapp.base.BaseApplication;
import com.lq.myapp.bean.VideoBean;
import com.lq.myapp.utils.LogUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchDataListAdapter extends RecyclerView.Adapter<SearchDataListAdapter.InnerHolder> {

    private static final String TAG = "SearchDataListAdapter";
    private List<VideoBean> data = new ArrayList<>();
    private OnItemClickListener mItemClickListener = null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_view, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, "" + position);
                mItemClickListener.onItemClick(position);
            }
        });
        holder.setItemData(data.get(position));
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
            this.data.clear();
            this.data.addAll(albumList);
        }
        BaseApplication.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public VideoBean getData(int position) {
        if (data != null && position < data.size()) {
            return data.get(position);
        } else
            return null;
    }

    class InnerHolder extends RecyclerView.ViewHolder{
        InnerHolder(View itemView) {
            super(itemView);
        }

        void setItemData(VideoBean videoBean) {
            ImageView videoCover = itemView.findViewById(R.id.video_cover);
            TextView videoName = itemView.findViewById(R.id.video_name);

            videoName.setText(videoBean.getTitle());
            Picasso.with(itemView.getContext())
                    .load(videoBean.getCoverURL())
                    .into(videoCover);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
