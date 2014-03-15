package ru.rgups.time.fragments;

import ru.rgups.time.adapters.BaseCalendarAdapter;
import ru.rgups.time.adapters.LessonCalendarAdapter;
import ru.rgups.time.adapters.TeacherLessonListAdapter;
import ru.rgups.time.model.DataManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.os.Bundle;

public class TeachersTimeTable extends BaseTameTableFragment{

	public static final String TEACHERS_NAME = "teachers_name";
	
	private TeacherLessonListAdapter mAdapter;
	private String mTeachersName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new TeacherLessonListAdapter(getActivity(), null, true);
		mTeachersName = getArguments().getString(TEACHERS_NAME);
	}
	
	@Override
	protected void setLessonAdapter(StickyListHeadersListView list) {
		list.setAdapter(mAdapter);
	}

	@Override
	protected void notifyAdapterSetChanged(int day, int weekState) {
		mAdapter.changeCursor(DataManager.getInstance().getTeachersLessons(day, weekState, mTeachersName));
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected BaseCalendarAdapter createNewCalendarAdapter() {
		return new LessonCalendarAdapter(getActivity());
	}

}