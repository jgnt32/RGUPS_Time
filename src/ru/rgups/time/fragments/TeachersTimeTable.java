package ru.rgups.time.fragments;

import ru.rgups.time.adapters.BaseCalendarAdapter;
import ru.rgups.time.adapters.TeacherLessonListAdapter;
import ru.rgups.time.adapters.TeachersCalendarAdapter;
import ru.rgups.time.model.DataManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.os.Bundle;
import android.util.Log;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

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
		TeachersCalendarAdapter adapter = new TeachersCalendarAdapter(getActivity());
		adapter.setTeachersName(getArguments().getString(TEACHERS_NAME));
		return adapter;
	}

	private class FullTimeRequestListener implements RequestListener<Boolean>{

		@Override
		public void onRequestFailure(SpiceException e) {
			e.printStackTrace();
			
		}

		@Override
		public void onRequestSuccess(Boolean response) {
			Log.e(getClass().getSimpleName(), "onRequestSuccess");
		}
		
	}
}
