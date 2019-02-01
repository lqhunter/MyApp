package com.lq.myapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.myapp.base.BaseActivity;
import com.lq.myapp.interfaces.IDetailViewCallback;
import com.lq.myapp.presenters.RadioDetailPresenter;
import com.lq.myapp.views.RoundRectImageView;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public class RadioDetailActivity extends BaseActivity implements IDetailViewCallback {

    private ImageView mLargeCover;
    private RoundRectImageView mSmallCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private RadioDetailPresenter mDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datail);

        //状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();
    }

    private void initView() {
        mLargeCover = findViewById(R.id.iv_large_cover);
        mSmallCover = findViewById(R.id.iv_small_cover);
        mAlbumTitle = findViewById(R.id.iv_title);
        mAlbumAuthor = findViewById(R.id.iv_author);

        mDetailPresenter = RadioDetailPresenter.getDetailPresenter();
        mDetailPresenter.registerViewCallback(this);
    }

    @Override
    public void onDetailListLoad(List<Track> tracks) {

    }

    @Override
    public void onAlbumLoad(Album album) {
        mAlbumTitle.setText(album.getAlbumTitle());
        mAlbumAuthor.setText(album.getAnnouncer().getNickname());

        Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover);
        Picasso.with(this).load(album.getCoverUrlSmall()).into(mSmallCover);
    }
}
