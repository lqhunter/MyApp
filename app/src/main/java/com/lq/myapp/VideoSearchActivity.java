package com.lq.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lq.myapp.adapters.SearchDataListAdapter;
import com.lq.myapp.bean.VideoBean;
import com.lq.myapp.interfaces.IVideoSearchViewCallBack;
import com.lq.myapp.presenters.VideoSearchPresenter;
import com.lq.myapp.utils.LogUtil;

import java.util.List;


public class VideoSearchActivity extends AppCompatActivity implements IVideoSearchViewCallBack, SearchDataListAdapter.OnItemClickListener{


    private static final String TAG = "VideoSearchActivity";
    private SearchView mSearchView;
    private Toolbar mToolbar;
    private SearchDataListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_search);

        initView();

        VideoSearchPresenter.getInstance().registerCallBack(this);

    }

    private void loadData(String key) {
        VideoSearchPresenter.getInstance().loadData(key);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        RecyclerView searchDataList = findViewById(R.id.search_data_list);
        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        searchDataList.setLayoutManager(gridLayoutManager);
        //设置适配器
        mAdapter = new SearchDataListAdapter();
        mAdapter.setOnItemClickListener(this);
        searchDataList.setAdapter(mAdapter);
    }


    @Override
    public void onBackPressed() {
        if (!mSearchView.isFocusable()) {
            LogUtil.d(TAG, mSearchView.isFocusable() + "");
            mSearchView.clearFocus();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setBackgroundColor(getResources().getColor(R.color.main_color));
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧有叉叉 可以关闭搜索框
        mSearchView.setIconified(false);
        mSearchView.setQueryHint("请输入剧名");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(VideoSearchActivity.this, query, Toast.LENGTH_SHORT).show();
                mToolbar.setTitle(query);
                loadData(query);
                //可以收起SearchView视图
                mSearchView.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onSuccess(List<VideoBean> data) {
        if (mAdapter != null) {
            mAdapter.setData(data);
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void noMore() {

    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (VideoSearchPresenter.hasInstance()) {
            VideoSearchPresenter.getInstance().unRegisterCallBack();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(VideoSearchActivity.this, VideoPlayActivity.class);
        intent.putExtra("video_bean", mAdapter.getData(position));
        startActivity(intent);
    }
}
