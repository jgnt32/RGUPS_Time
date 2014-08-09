package ru.rgups.time.fragments;

import ru.rgups.time.adapters.BaseCalendarAdapter;
import ru.rgups.time.adapters.LessonListPagerAdapter;
import ru.rgups.time.adapters.TeacherLessonListAdapter;
import ru.rgups.time.adapters.TeacherPagerAdapter;
import ru.rgups.time.adapters.TeachersCalendarAdapter;
import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.utils.CalendarManager;

import android.os.Bundle;
import android.widget.ListView;

public class TeachersTimeTable extends BaseTameTableFragment{

	public static final String TEACHERS_NAME = "teachers_name";
	
	private TeacherLessonListAdapter mAdapter;
	private String mTeachersName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mAdapter = new TeacherLessonListAdapter(getActivity(), null, false);
		mTeachersName = getArguments().getString(TEACHERS_NAME);
		super.onCreate(savedInstanceState);

	}
	
	@Override
	protected void setLessonAdapter(ListView list) {
		list.setAdapter(mAdapter);
	}

	@Override
	protected void notifyAdapterSetChanged(int day, int weekState) {
		mAdapter.changeCursor(DataManager.getInstance().getTeachersLessons(day, weekState, mTeachersName));
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected BaseCalendarAdapter createNewCalendarAdapter() {
		TeachersCalendarAdapter adapter = new TeachersCalendarAdapter(getActivity(), mTeachersName);
		return adapter;
	}



	@Override
	protected LessonListPagerAdapter getNewPagerAdapter() {
		return new TeacherPagerAdapter(getChildFragmentManager(), CalendarManager.getCorrectDayCount(), mTeachersName);
	}
}
