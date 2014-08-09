package ru.rgups.time.fragments;

import ru.rgups.time.adapters.BaseCalendarAdapter;
import ru.rgups.time.adapters.LessonListPagerAdapter;
import ru.rgups.time.adapters.TeacherLessonListAdapter;
import ru.rgups.time.adapters.TeacherPagerAdapter;
import ru.rgups.time.adapters.TeachersCalendarAdapter;
import ru.rgups.time.loaders.LessonExistingVector;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.utils.CalendarManager;


import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public class TeachersTimeTable extends BasePageTameTableFragment implements LoaderManager.LoaderCallbacks<boolean[][]>{

	public static final String TEACHERS_NAME = "teachers_name";
	
	private String mTeachersName;
    private TeachersCalendarAdapter mCalendarAdapter;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		mTeachersName = getArguments().getString(TEACHERS_NAME);
		super.onCreate(savedInstanceState);
	}

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }

    @Override
	protected BaseCalendarAdapter createNewCalendarAdapter() {
		mCalendarAdapter = new TeachersCalendarAdapter(getActivity(), mTeachersName);
		return mCalendarAdapter;
	}


	@Override
	protected LessonListPagerAdapter getNewPagerAdapter() {
		return new TeacherPagerAdapter(getChildFragmentManager(), CalendarManager.getCorrectDayCount(), mTeachersName);
	}

    @Override
    public Loader<boolean[][]> onCreateLoader(int id, Bundle args) {
        return new LessonExistingVector(getActivity(), mTeachersName);
    }

    @Override
    public void onLoadFinished(Loader<boolean[][]> loader, boolean[][] data) {
        mCalendarAdapter.setmLessonMatrix(data);
        mCalendarAdapter.notifyDataSetChanged();
    }


    @Override
    public void onLoaderReset(Loader<boolean[][]> loader) {

    }
}
