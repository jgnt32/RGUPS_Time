package ru.rgups.time.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import ru.rgups.time.R;
import ru.rgups.time.adapters.gallery.GalleryViewPager;
import ru.rgups.time.adapters.gallery.UrlPagerAdapter;

/**
 * Created by timewaistinguru on 11.08.2014.
 */
public class PhotoFullScreenActivity extends ActionBarActivity {

    public static final String PHOTOS = "home_works_photo";

    private GalleryViewPager mViewPager;
    private UrlPagerAdapter mPhotosAdapter;

    private List<String> mPhotos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity);
        mPhotos  =  getIntent().getStringArrayListExtra(PHOTOS);
        mPhotosAdapter = new UrlPagerAdapter(this, mPhotos);
        mViewPager = (GalleryViewPager) findViewById(R.id.galery_view_pager);
        mViewPager.setAdapter(mPhotosAdapter);
    }
}
