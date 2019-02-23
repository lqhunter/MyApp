package com.lq.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjm.bottomtabbar.BottomTabBar;
import com.lq.myapp.fragments.PicFragment;
import com.lq.myapp.fragments.RadioFragment;
import com.lq.myapp.fragments.VideoFragment;
import com.lq.myapp.utils.LogUtil;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.auth.component.XmlyBrowserComponent;
import com.ximalaya.ting.android.opensdk.auth.handler.XmlySsoHandler;
import com.ximalaya.ting.android.opensdk.auth.model.XmlyAuthInfo;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.httputil.XimalayaException;
import com.ximalaya.ting.android.opensdk.model.user.XmBaseUserInfo;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;
    private ImageView mUser;
    private boolean isLogging = false;

    /**
     * 喜马拉雅授权实体类对象
     */
    private XmlyAuthInfo mAuthInfo;
    /**
     * 喜马拉雅授权管理类对象
     */
    private XmlySsoHandler mSsoHandler;
    /**
     * 当前 DEMO 应用的回调页，第三方应用应该使用自己的回调页。
     */
//    public static final String REDIRECT_URL =  "http://api.ximalaya.com";
    public static final String REDIRECT_URL = DTransferConstants.isRelease ?
            "http://api.ximalaya.com/openapi-collector-app/get_access_token" :
            "http://studio.test.ximalaya.com/qunfeng-web/access_token/callback";
    private TextView mUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLogin();
        initView();
    }

    private void initLogin() {
        try {
            mAuthInfo = new XmlyAuthInfo(this, CommonRequest.getInstanse().getAppKey(), CommonRequest.getInstanse()
                    .getPackId(), REDIRECT_URL, DTransferConstants.isRelease ? CommonRequest.getInstanse().getAppKey() : "qunfeng");
        } catch (XimalayaException e) {
            e.printStackTrace();
        }
        mSsoHandler = new XmlySsoHandler(this, mAuthInfo);
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
        View headerView = navigationView.getHeaderView(0);
        mUser = headerView.findViewById(R.id.iv_user);
        mUsername = headerView.findViewById(R.id.user_name);
        mUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogging) {
                    mSsoHandler.authorizeWeb(new CustomAuthListener() {
                        @Override
                        public void initUserInfo() {
                            Map<String ,String > map = new HashMap<>();
                            CommonRequest.getBaseUserInfo(map, new IDataCallBack<XmBaseUserInfo>() {
                                @Override
                                public void onSuccess(@Nullable XmBaseUserInfo xmBaseUserInfo) {
                                    mUsername.setText(xmBaseUserInfo.getNickName());
                                    Picasso.with(MainActivity.this).load(xmBaseUserInfo.getAvatarUrl()).into(mUser);
                                }

                                @Override
                                public void onError(int i, String s) {
                                    LogUtil.e(TAG, "获取用户信息失败");
                                }
                            });
                            isLogging = true;
                        }
                    });
                }

            }
        });

        BottomTabBar bottomTabBar = findViewById(R.id.bottom_tab_bar);
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(50, 50)//设置ICON图片的尺寸
                .setFontSize(12)//设置文字的尺寸
                .setTabPadding(10, 6, 10)//设置ICON图片与上部分割线的间隔、图片与文字的间隔、文字与底部的间隔
                .addTabItem("图片", R.mipmap.picture, PicFragment.class)//设置文字、一张图片、fragment
                .addTabItem("电台", R.mipmap.radio, RadioFragment.class)
                .addTabItem("美剧", R.mipmap.movie, VideoFragment.class)
                .isShowDivider(false)//设置是否显示分割线
                //.setTabBarBackgroundResource(R.drawable.bg_white)//设置底部导航栏的背景图片【与设置底部导航栏颜色方法不能同时使用，否则会覆盖掉前边设置的颜色】
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
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.search:
                        Intent intent = new Intent(MainActivity.this, VideoSearchActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.tool_bar_download:
                        Toast.makeText(MainActivity.this, "点击了下载", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
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
        if (mToolbar != null) {
            mToolbar.setTitle("电台");
            mToolbar.getMenu().findItem(R.id.search).setVisible(true);
        }

    }

    private void changeToolBarVideo() {
        mToolbar.setTitle("美剧");
        mToolbar.getMenu().findItem(R.id.search).setVisible(true);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XmPlayerManager.release();
    }
}
