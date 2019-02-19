package com.lq.myapp.adapters;

import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimi_wu.ptlrecyclerview.AutoLoad.AutoLoadRecyclerView;
import com.lq.myapp.R;
import com.lq.myapp.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class RadioDetailListViewAdapter extends AutoLoadRecyclerView.Adapter<RadioDetailListViewAdapter.InnerHolder> {

    private static final String TAG = "RadioDetailListViewAdapter";
    private List<Track> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener = null;
    private int selectedIndex = -1;


    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_detail_item_view, parent, false);
        return new InnerHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        LogUtil.d(TAG, "onBindViewHolder" + position);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        holder.setItemData(position);
        if (position == selectedIndex) {
            holder.setAnimation();
            holder.startAnimation();
        } else {
            holder.stopAnimation();
        }
    }

    public void setData(List<Track> tracks) {
        if (tracks != null) {
            this.data.addAll(tracks);
        }
    }

    public void setSelectedIndex(int position) {
        LogUtil.d(TAG, "setSelectedIndex.." + position);
        this.selectedIndex = position;
        //this.notifyItemChanged(position);
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public class InnerHolder extends AutoLoadRecyclerView.ViewHolder {

        private TextView mNumber;
        private TextView mTitle;
        private TextView mPlay_count;
        private ImageView mPlayFlag;
        private AnimationDrawable mBackground;

        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setItemData(int position) {
            Track track = data.get(position);
            mNumber = itemView.findViewById(R.id.track_number);
            mTitle = itemView.findViewById(R.id.track_title);
            mPlay_count = itemView.findViewById(R.id.track_play_count);
            mPlayFlag = itemView.findViewById(R.id.iv_isplaying);

            mNumber.setText(position + 1 + "");
            mTitle.setText(track.getTrackTitle());
            mPlay_count.setText(track.getPlayCount() + "");

        }

        public void setAnimation() {
            mPlayFlag.setBackgroundResource(R.drawable.animation_list);
            mBackground = (AnimationDrawable) mPlayFlag.getBackground();
            //是否仅仅启动一次？
            mBackground.setOneShot(false);
        }

        public void startAnimation() {
            if (mBackground != null) {
                mPlayFlag.setVisibility(View.VISIBLE);
                mNumber.setVisibility(View.INVISIBLE);
                mBackground.start();
            }
        }

        public void stopAnimation() {
            if (mBackground != null) {
                mPlayFlag.setVisibility(View.INVISIBLE);
                mNumber.setVisibility(View.VISIBLE);
                mBackground.stop();
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
