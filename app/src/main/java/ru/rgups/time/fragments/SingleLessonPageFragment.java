package ru.rgups.time.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import ru.rgups.time.R;
import ru.rgups.time.adapters.SingleLessonPageAdapter;

/**
 * Created by timewaistinguru on 12.08.2014.
 */
public class SingleLessonPageFragment extends Fragment {

    private ViewPager mPager;
    private SingleLessonPageAdapter mPageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageAdapter = new SingleLessonPageAdapter(getChildFragmentManager(), getActivity(), getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.single_lesson_pager_fragment, null);
        PagerSlidingTabStrip mTabStrip = (PagerSlidingTabStrip) v.findViewById(R.id.single_lessons_tabs);
        mPager = (ViewPager) v.findViewById(R.id.sengle_lesson_pager);
        mPager.setAdapter(mPageAdapter);
        mTabStrip.setViewPager(mPager);
        return v;
    }
}
