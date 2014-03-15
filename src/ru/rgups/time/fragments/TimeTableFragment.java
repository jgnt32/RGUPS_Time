package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.adapters.LessonAdapter;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.entity.LessonList;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.os.Bundle;
import android.util.Log;

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
		DataManager.getInstance().setSpiceManager(getSpiceManager());
		DataManager.getInstance().timeTableRequest(new GetTimeListener());
	}


	
	private class GetTimeListener implements RequestListener< LessonList >{
		
		
		@Override
		public void onRequestFailure(SpiceException exception) {
		}

		@Override
		public void onRequestSuccess(LessonList list) {
			Log.e("list",""+list.getDays().size());
		}
	}


	@Override
	protected void setLessonAdapter(StickyListHeadersListView list) {
		list.setAdapter(mLessonAdapter);
	}


	@Override
	protected void notifyAdapterSetChanged(int day, int weekState) {
		mLessons.clear();
		mLessonAdapter.setTimestamp(getTimeStamp());
		mLessons.addAll(DataManager.getInstance().getLessonList(day, weekState));
		mLessonAdapter.notifyDataSetChanged();		
	}


}
