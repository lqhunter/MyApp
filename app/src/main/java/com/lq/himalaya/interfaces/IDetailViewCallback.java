package com.lq.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IDetailViewCallback {
    //把tracks传给UI
    void onDetailListLoad(List<Track> tracks);

    //把Album传给UI
    void onAlbumLoad(Album album);


}
