package ru.rgups.time.adapters;

import ru.rgups.time.fragments.StudentLessonFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class LessonListPagerAdapter extends FragmentPagerAdapter{
	
	private int mCount;

	public LessonListPagerAdapter(FragmentManager fm, int count) {
		super(fm);
		mCount = count;
	}

	@Override
	public Fragment getItem(int position) {
		Bundle args = new Bundle();
		args.putInt(StudentLessonFragment.DAY_ARGS, position);
		StudentLessonFragment fragment = new StudentLessonFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCount;
	}
	
	

}
