package ru.rgups.time.fragments;

import ru.rgups.time.adapters.TeacherLessonListAdapter;
import ru.rgups.time.datamanagers.LessonManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TeacherLessonListFragment extends LessonListFragment{
	
	public final static String TEACHER_ARGS = "teacher_args";

	private int mDayNumber;
	private String mTeacherName;
	private Cursor mCursor;
	
	private TeacherLessonListAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mDayNumber = getArguments().getInt(DAY_ARGS);
		mTeacherName = getArguments().getString(TEACHER_ARGS);
		mAdapter = new TeacherLessonListAdapter(getActivity(), null, false);

		super.onCreate(savedInstanceState);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}

	@Override
	protected void setAdapter(ListView list) {
		list.setAdapter(mAdapter);
	}

	@Override
	protected void loadLessonsFromDb() {
		mCursor = LessonManager.getInstance().getTeachersLessonsBySemestrDay(mDayNumber, mTeacherName);
	}

	@Override
	protected void notifyAdtapter() {
		mAdapter.changeCursor(mCursor);
		mAdapter.notifyDataSetChanged();
	}

}
