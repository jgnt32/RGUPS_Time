package ru.rgups.time.fragments;

import java.io.IOException;

import ru.rgups.time.adapters.BaseCalendarAdapter;
import ru.rgups.time.adapters.LessonCalendarAdapter;
import ru.rgups.time.adapters.LessonListPagerAdapter;
import ru.rgups.time.loaders.LessonExistingVector;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.rest.RestManager;
import ru.rgups.time.utils.CalendarManager;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class StudentTimeTableFragment extends BasePageTameTableFragment implements LoaderManager.LoaderCallbacks<boolean[][]> {
	
	public final static String DAY_MONTH_DATE_FORMAT = "d MMMM";
	public static final String DAY_OF_WEEK_DATE_FORMAT = "EEEE";
	
	private LessonCalendarAdapter mCalendarAdapter;
		
	@Override
	public void onResume() {
		super.onResume();
	    getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
	    RestManager.getInstance().timeTableRequest(null);
    }

    @Override
    public android.support.v4.content.Loader<boolean[][]> onCreateLoader(int id, Bundle args) {
        return new LessonExistingVector(getActivity(), null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<boolean[][]> loader, boolean[][] data) {
        mCalendarAdapter.setmLessonMatrix(data);
        mCalendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<boolean[][]> loader) {

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
	protected BaseCalendarAdapter createNewCalendarAdapter() {
		mCalendarAdapter = new LessonCalendarAdapter(getActivity());
		return mCalendarAdapter;
	}


	@Override
	protected LessonListPagerAdapter getNewPagerAdapter() {
		return new LessonListPagerAdapter(getChildFragmentManager(), CalendarManager.getCorrectDayCount());
	}


}
