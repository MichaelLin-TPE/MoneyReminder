package com.money.moneyreminder.tool;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.money.moneyreminder.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoaderProvider {

    private static ImageLoaderProvider instance = null;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options;

    public static ImageLoaderProvider getInstance(){
        if (instance == null){
            instance = new ImageLoaderProvider();

            return instance;
        }
        return instance;
    }

    public  void initImageLoader() {
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MoneyReminderApplication.getInstance().getApplicationContext())
                .defaultDisplayImageOptions(options).build();
        imageLoader.init(config);
    }
    public void setImage(String photoUrl , ImageView imageView){
        imageLoader.displayImage(photoUrl,imageView);
    }
}
