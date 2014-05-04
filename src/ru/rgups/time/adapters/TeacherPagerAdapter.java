package ru.rgups.time.adapters;

import ru.rgups.time.fragments.TeacherLessonListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class TeacherPagerAdapter extends LessonListPagerAdapter{
	
	private String mTeacherName;

	public TeacherPagerAdapter(FragmentManager fm, int count, String name) {
		super(fm, count);
		mTeacherName = name;
	}

	
	@Override
	public Fragment getItem(int position) {
		Bundle args = new  Bundle();
		args.putInt(TeacherLessonListFragment.DAY_ARGS, position);
		args.putString(TeacherLessonListFragment.TEACHER_ARGS, mTeacherName);
		TeacherLessonListFragment fragment = new TeacherLessonListFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
