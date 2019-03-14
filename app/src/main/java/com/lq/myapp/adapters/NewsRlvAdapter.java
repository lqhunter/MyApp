package com.lq.myapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lq.myapp.R;
import com.lq.myapp.bean.News;
import com.lq.myapp.views.RoundRectImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lqhunter
 * date : 2019/3/13 0013
 * description :
 */

public class NewsRlvAdapter extends RecyclerView.Adapter<NewsRlvAdapter.InnerHolder> {

    List<News.ItemListBean> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_rlv_view, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(data.get(position).getDetailUrl());
                }
            }
        });
        holder.setData(position);
    }

    public void setData(List<News.ItemListBean> data) {
        if (data != null && data.size() != 0) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private RoundRectImageView mCover;

        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            News.ItemListBean bean = data.get(position);
            mTitle = itemView.findViewById(R.id.news_title);
            mCover = itemView.findViewById(R.id.news_cover);

            mTitle.setText(bean.getItemTitle());
            Picasso.with(itemView.getContext()).load(bean.getItemImageNew().get(0).getImgUrl()).into(mCover);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(String url);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
