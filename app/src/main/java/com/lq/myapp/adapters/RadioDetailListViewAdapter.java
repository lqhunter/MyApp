package com.lq.myapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lq.myapp.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class RadioDetailListViewAdapter extends RecyclerView.Adapter<RadioDetailListViewAdapter.InnerHolder> {

    private List<Track> data = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_detail_item_view, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setItemData(position);
    }

    public void setData(List<Track> tracks) {
        if (tracks != null) {
            this.data.addAll(tracks);
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

        private TextView mNumber;
        private TextView mTitle;
        private TextView mPlay_count;

        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setItemData(int position) {
            Track track = data.get(position);
            mNumber = itemView.findViewById(R.id.track_number);
            mTitle = itemView.findViewById(R.id.track_title);
            mPlay_count = itemView.findViewById(R.id.track_play_count);

            mNumber.setText(position + 1 + "");
            mTitle.setText(track.getTrackTitle());
            mPlay_count.setText(track.getPlayCount()+ "");

        }
    }
}
