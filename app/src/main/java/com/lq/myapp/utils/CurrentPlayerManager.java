package com.lq.myapp.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import com.lq.myapp.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.ArrayList;
import java.util.List;

/**
 * 此manager 管理当前播放器的 albums, tracks等
 */
public class CurrentPlayerManager {

    private Album mAlbum;
    private List<Track> mTracks = new ArrayList<>();
    private XmPlayListControl.PlayMode mPlayMode;

    private static CurrentPlayerManager instance = null;


    public XmPlayListControl.PlayMode getPlayMode() {
        return mPlayMode;
    }

    public void setPlayMode(XmPlayListControl.PlayMode playMode) {
        mPlayMode = playMode;
    }

    private CurrentPlayerManager() {
        mPlayMode = XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
    }

    public static CurrentPlayerManager getInstance() {
        if (instance == null) {
            synchronized (CurrentPlayerManager.class) {
                if (instance == null) {
                    instance = new CurrentPlayerManager();
                }
            }
        }
        return instance;
    }

    public Album getAlbum() {
        return mAlbum;
    }

    public void setAlbum(Album album) {
        mAlbum = album;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    public XmPlayListControl.PlayMode getNextPlayMode() {
        if (mPlayMode == XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP) {
            mPlayMode = XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP;
        } else if (mPlayMode == XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP){
            mPlayMode = XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM;
        } else if (mPlayMode == XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM) {
            mPlayMode = XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
        }
        return mPlayMode;
    }
}
