package com.lq.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hjm.bottomtabbar.BottomTabBar;
import com.lq.myapp.fragments.PicFragment;
import com.lq.myapp.fragments.RadioFragment;
import com.lq.myapp.fragments.VideoFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
        setSupportActionBar(mToolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        //初始化图片界面不显示搜索图标
        mToolbar.getMenu().findItem(R.id.search).setVisible(false);
        return true;
    }

    private void changeToolBarPic() {
        Log.d(TAG, "changeToolBarPic...");

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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Log.d(TAG, "nav_camera...");

            // Handle the camera action
        } else if (id == R.id.nav_history) {
            Log.d(TAG, "nav_gallery...");

        } else if (id == R.id.nav_download) {
            Log.d(TAG, "nav_slideshow...");

        } else if (id == R.id.nav_favorite) {
            Log.d(TAG, "nav_manage...");

        } else if (id == R.id.nav_share) {
            Log.d(TAG, "nav_share...");

        } else if (id == R.id.nav_send) {
            Log.d(TAG, "nav_send...");

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }
}
