package ru.rgups.time.adapters.gallery;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by timewaistinguru on 11.08.2014.
 */
public class PhotoPageAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return false;
    }
}
