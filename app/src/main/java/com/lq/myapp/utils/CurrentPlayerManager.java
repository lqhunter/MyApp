package com.lq.myapp.utils;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * 此manager 管理当前播放器的 albums, tracks等
 */
public class CurrentPlayerManager {

    private Album mAlbum;
    private List<Track> mTracks = new ArrayList<>();


    private static CurrentPlayerManager instance = null;

    private CurrentPlayerManager() {

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
}
