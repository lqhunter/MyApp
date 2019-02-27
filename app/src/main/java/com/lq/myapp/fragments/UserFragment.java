package com.lq.myapp.fragments;

import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lq.myapp.activities.CustomAuthListener;
import com.lq.myapp.R;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.utils.LogUtil;
import com.lq.myapp.utils.RoundImageView;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.auth.handler.XmlySsoHandler;
import com.ximalaya.ting.android.opensdk.auth.model.XmlyAuth2AccessToken;
import com.ximalaya.ting.android.opensdk.auth.model.XmlyAuthInfo;
import com.ximalaya.ting.android.opensdk.auth.utils.AccessTokenKeeper;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.AccessTokenManager;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.datatrasfer.ILoginOutCallBack;
import com.ximalaya.ting.android.opensdk.httputil.XimalayaException;
import com.ximalaya.ting.android.opensdk.model.user.XmBaseUserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * author : lqhunter
 * date : 2019/2/23 0023
 * description :
 */
public class UserFragment extends BaseFragment {
    private static final String TAG = "UserFragment";
    /**
     * 封装了 "access_token"，"refresh_token"，并提供了他们的管理功能
     */
    private XmlyAuth2AccessToken mAccessToken;
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

    private View mRootView;
    private TextView mUser_name;
    private RoundImageView mUser_pic;
    private boolean isLogin = false;


    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = layoutInflater.inflate(R.layout.fragment_main_user, container, false);
        initView();
        initLogin();
        return mRootView;
    }

    @Override
    public void initData() {
        mAccessToken = AccessTokenKeeper.readAccessToken(getContext());
        if (mAccessToken != null && mAccessToken.isSessionValid()) {//token有效就可以初始化用户信息
            Map<String, String> map = new HashMap<>();
            CommonRequest.getBaseUserInfo(map, new IDataCallBack<XmBaseUserInfo>() {
                @Override
                public void onSuccess(@Nullable XmBaseUserInfo xmBaseUserInfo) {

                    //登录成功
                    isLogin = true;
                    mUser_name.setText(xmBaseUserInfo.getNickName());
                    Picasso.with(getContext()).load(xmBaseUserInfo.getAvatarUrl()).into(mUser_pic);
                }

                @Override
                public void onError(int i, String s) {
                    LogUtil.e(TAG, "初始化数据错误id。。。" + i);
                    mUser_name.setText("点击头像登录");
                    mUser_pic.setBackground(getResources().getDrawable(R.mipmap.login_avatar));
                }
            });
        } else {
            mUser_name.setText("点击头像登录");
            mUser_pic.setBackground(getResources().getDrawable(R.mipmap.login_avatar));
        }
    }

    private void initView() {

        mUser_name = mRootView.findViewById(R.id.tv_user_name);
        mUser_pic = mRootView.findViewById(R.id.iv_user);
        mUser_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAccessToken = AccessTokenKeeper.readAccessToken(getContext());
                if (mAccessToken == null || !mAccessToken.isSessionValid()) {//Token为空，无效就去请求token
                    mSsoHandler.authorizeWeb(new CustomAuthListener() {
                        @Override
                        public void initUserInfo() {
                            Map<String, String> map = new HashMap<>();
                            CommonRequest.getBaseUserInfo(map, new IDataCallBack<XmBaseUserInfo>() {
                                @Override
                                public void onSuccess(@Nullable XmBaseUserInfo xmBaseUserInfo) {
                                    //登录成功
                                    isLogin = true;
                                    mUser_name.setText(xmBaseUserInfo.getNickName());
                                    Picasso.with(getContext()).load(xmBaseUserInfo.getAvatarUrl()).into(mUser_pic);
                                }

                                @Override
                                public void onError(int i, String s) {
                                    LogUtil.e(TAG, "点击登录获取错误id。。。" + i);

                                }
                            });
                        }
                    });
                } else {
                    //TODO 显示dialog
                    showPopWindow();
                }

            }
        });
    }

    private void initLogin() {
        try {
            mAuthInfo = new XmlyAuthInfo(getActivity(), CommonRequest.getInstanse().getAppKey(), CommonRequest.getInstanse()
                    .getPackId(), REDIRECT_URL, DTransferConstants.isRelease ? CommonRequest.getInstanse().getAppKey() : "qunfeng");
        } catch (XimalayaException e) {
            e.printStackTrace();
        }
        mSsoHandler = new XmlySsoHandler(getActivity(), mAuthInfo);
    }

    private void showPopWindow() {
        View parent = ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(getContext(), R.layout.user_popup_window, null);

        TextView changeUser = popView.findViewById(R.id.user_change);
        TextView logOut = popView.findViewById(R.id.user_log_out);
        TextView cancel = popView.findViewById(R.id.cancle);
        ImageView bgShadow = popView.findViewById(R.id.bg_shadow);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView,width,height);
        popWindow.setAnimationStyle(R.style.PopupAnimation);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置同意在外点击消失
        popWindow.setClippingEnabled(false);//覆盖状态栏

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                LogUtil.d(TAG, v.getId() + "");
                switch (v.getId()) {
                    case R.id.user_change:

                        break;
                    case R.id.user_log_out:
                        AccessTokenManager.getInstanse().loginOut(new ILoginOutCallBack() {
                            @Override
                            public void onSuccess() {
                                if (mAccessToken != null && mAccessToken.isSessionValid()) {
                                    AccessTokenKeeper.clear(getContext());
                                    mAccessToken = new XmlyAuth2AccessToken();
                                }
                                //退出后权限认证失败，此时会102错误
                                LogUtil.d(TAG, "退出登录成功...");
                                //TODO:重置用户头像等
                                isLogin = false;
                                getActivity().recreate();
                            }

                            @Override
                            public void onFail(int i, String s) {
                                LogUtil.e(TAG, "错误id。。。" + i);

                            }
                        });
                        break;
                    case R.id.cancle:
                        break;
                    case R.id.bg_shadow:
                        break;
                }
                popWindow.dismiss();
            }
        };

        changeUser.setOnClickListener(listener);
        logOut.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
        bgShadow.setOnClickListener(listener);

        popWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
    }
}
