package ru.rgups.time.fragments;

import java.io.IOException;
import java.util.ArrayList;

import ru.rgups.time.adapters.BaseCalendarAdapter;
import ru.rgups.time.adapters.LessonAdapter;
import ru.rgups.time.adapters.LessonCalendarAdapter;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.rest.RestManager;
import android.os.Bundle;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class TimeTableFragment extends BaseTameTableFragment {
	
	public final static String DAY_MONTH_DATE_FORMAT = "d MMMM";
	public static final String DAY_OF_WEEK_DATE_FORMAT = "EEEE";
	
	private ArrayList<LessonListElement> mLessons = new ArrayList<LessonListElement>();
	private LessonAdapter mLessonAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		mLessons = new ArrayList<LessonListElement>();
		mLessonAdapter = new LessonAdapter(getActivity(), mLessons);
		mLessonAdapter.setTimestamp(getTimeStamp());
	}
	
		
	@Override
	public void onResume() {
		super.onResume();
		RestManager.getInstance().setSpiceManager(getSpiceManager());
		RestManager.getInstance().timeTableRequest(new GetTimeListener());
	}
	
	private class GetTimeListener implements RequestListener< LessonList >{
		
		
		@Override
		public void onRequestFailure(SpiceException exception) {
		}

		@Override
		public void onRequestSuccess(LessonList list) {
			try {
				DataManager.getInstance().writeToSD(getActivity());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	protected void setLessonAdapter(ListView list) {
		list.setAdapter(mLessonAdapter);
	}


	@Override
	protected void notifyAdapterSetChanged(int day, int weekState) {
		mLessons.clear();
		mLessonAdapter.setTimestamp(getTimeStamp());
		mLessons.addAll(DataManager.getInstance().getLessonList(day, weekState));
		mLessonAdapter.notifyDataSetChanged();		
	}


	@Override
	protected BaseCalendarAdapter createNewCalendarAdapter() {
		return new LessonCalendarAdapter(getActivity());
	}


}
