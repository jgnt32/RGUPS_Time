package ru.rgups.time.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.rgups.time.fragments.TeacherLessonListFragment;

public class TeacherPagerAdapter extends LessonListPagerAdapter{
	
	private long mTeacherId;

	public TeacherPagerAdapter(FragmentManager fm, int count, long teacherId) {
		super(fm, count);
        mTeacherId = teacherId;
	}

	
	@Override
	public Fragment getItem(int position) {
		Bundle args = new  Bundle();
		args.putInt(TeacherLessonListFragment.DAY_ARGS, position);
		args.putLong(TeacherLessonListFragment.TEACHER_ARGS, mTeacherId);
		TeacherLessonListFragment fragment = new TeacherLessonListFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
