package com.lq.myapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by lqhunter on 2018/4/1.
 */

public class WallpaperViewPagerAdapter extends PagerAdapter {

    private final Context context;
    private List<String> imageUrls = new ArrayList<>();


    public WallpaperViewPagerAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls.get(position);
        PhotoView photoView = new PhotoView(context);
        photoView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context)
                .load(url)
                .memoryPolicy(NO_CACHE, NO_STORE)//加载大图不从内存查找，加载完成后不加入内存
                .into(photoView);
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity.finish();
            }
        });
        return photoView;
    }

    public void setData(List<String> imageUrls) {
        if (imageUrls != null) {
            this.imageUrls.addAll(imageUrls);
        }
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
