package ru.rgups.time.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.rgups.time.R;
import ru.rgups.time.fragments.LessonsHomeWorksFragment;
import ru.rgups.time.fragments.SingleLessonFragment;

/**
 * Created by timewaistinguru on 12.08.2014.
 */
public class SingleLessonPageAdapter extends FragmentPagerAdapter {

    public static final int LESSON_INFO = 0;
    public static final int LESSON_HW = 1;

    private Bundle mArguments;

    private Context mContext;

    public SingleLessonPageAdapter(FragmentManager fm, Context context, Bundle args) {
        super(fm);
        mArguments = args;
        mContext = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case LESSON_INFO:

                return mContext.getString(R.string.single_lesson_info);
            case LESSON_HW:

                return mContext.getString(R.string.single_lesson_hw);
            default:        return super.getPageTitle(position);

        }
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case LESSON_INFO:
                return getSingleLessonFragment();

            case LESSON_HW:
                return getHomeWorksFragment();

            default:  return null;

        }
    }

    private SingleLessonFragment getSingleLessonFragment() {
        SingleLessonFragment result = new SingleLessonFragment();
        result.setArguments(mArguments);
        return result;
    }

    private LessonsHomeWorksFragment getHomeWorksFragment(){
        LessonsHomeWorksFragment result = new LessonsHomeWorksFragment();
        result.setArguments(mArguments);
        return result;
    }

    @Override
    public int getCount() {
        return 2;
    }


}
