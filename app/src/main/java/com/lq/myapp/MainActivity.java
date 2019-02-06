package com.lq.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.hjm.bottomtabbar.BottomTabBar;
import com.lq.myapp.fragments.PicFragment;
import com.lq.myapp.fragments.RadioFragment;
import com.lq.myapp.fragments.VideoFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        //initToolBar
        mToolbar = findViewById(R.id.test_tool_bar);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        //默认图片界面
        changeToolBarPic();
        //SearchView searchView = (SearchView) mToolbar.getMenu().findItem(R.id.search).getActionView();
        //设置提交按钮是否可见
        //searchView.setSubmitButtonEnabled(true);

        BottomTabBar bottomTabBar = findViewById(R.id.bottom_tab_bar);
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(50, 50)//设置ICON图片的尺寸
                .setFontSize(12)//设置文字的尺寸
                .setTabPadding(4, 6, 10)//设置ICON图片与上部分割线的间隔、图片与文字的间隔、文字与底部的间隔
                .addTabItem("图片", R.drawable.picture, PicFragment.class)//设置文字、一张图片、fragment
                .addTabItem("电台", R.drawable.radio, RadioFragment.class)
                .addTabItem("美剧", R.drawable.movie, VideoFragment.class)
                .isShowDivider(false)//设置是否显示分割线
                .setTabBarBackgroundColor(Color.WHITE)//设置底部导航栏颜色
                //.setTabBarBackgroundResource(R.mipmap.ic_launcher)//设置底部导航栏的背景图片【与设置底部导航栏颜色方法不能同时使用，否则会覆盖掉前边设置的颜色】
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name, View view) {
                        switch (position) {
                            case 0:
                                changeToolBarPic(); //改变对应的toolbar视图
                                break;
                            case 1:
                                changeToolBarRadio();
                                break;
                            case 2:
                                changeToolBarVideo();
                                break;
                        }
                        Log.d(TAG, "position" + position);
                    }
                })
                .setCurrentTab(0);   //设置当前选中的Tab，从0开始

    }

    private void changeToolBarPic() {
        mToolbar.setTitle("图片");
        //图片界面关闭搜索
        mToolbar.getMenu().findItem(R.id.search).setVisible(false);
    }


    private void changeToolBarRadio() {
        mToolbar.setTitle("电台");
        mToolbar.getMenu().findItem(R.id.search).setVisible(true);
        SearchView searchView = (SearchView) mToolbar.getMenu().findItem(R.id.search).getActionView();
        searchView.setQueryHint("请输入电台名");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO:电台搜索跳转界面
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void changeToolBarVideo() {
        mToolbar.setTitle("美剧");
        mToolbar.getMenu().findItem(R.id.search).setVisible(true);

        SearchView searchView = (SearchView) mToolbar.getMenu().findItem(R.id.search).getActionView();
        searchView.setQueryHint("请输入剧名");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, VideoSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("searchKey", query);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


}
