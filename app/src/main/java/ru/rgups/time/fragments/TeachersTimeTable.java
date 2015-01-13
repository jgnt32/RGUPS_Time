package ru.rgups.time.fragments;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import ru.rgups.time.adapters.BaseCalendarAdapter;
import ru.rgups.time.adapters.LessonListPagerAdapter;
import ru.rgups.time.adapters.TeacherPagerAdapter;
import ru.rgups.time.adapters.TeachersCalendarAdapter;
import ru.rgups.time.loaders.LessonExistingVector;
import ru.rgups.time.model.entity.StudentCalendarLessonInfo;
import ru.rgups.time.rest.RestManager;
import ru.rgups.time.utils.CalendarManager;

public class TeachersTimeTable extends BasePageTameTableFragment implements LoaderManager.LoaderCallbacks<StudentCalendarLessonInfo>{

	public static final String TEACHERS_NAME = "teachers_name";
	
	private long mTeachersId;
    private TeachersCalendarAdapter mCalendarAdapter;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		mTeachersId = getArguments().getLong(TEACHERS_NAME);
		super.onCreate(savedInstanceState);
	}

    @Override
    public void onResume() {
        super.onResume();
        RestManager.getInstance().teacherLessonRequest(null, this.getActivity().getApplication(), mTeachersId);

        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }

    @Override
	protected BaseCalendarAdapter createNewCalendarAdapter() {
		mCalendarAdapter = new TeachersCalendarAdapter(getActivity(), mTeachersId);
		return mCalendarAdapter;
	}


	@Override
	protected LessonListPagerAdapter getNewPagerAdapter() {
		return new TeacherPagerAdapter(getChildFragmentManager(), CalendarManager.getCorrectSemestrDayCount(), mTeachersId);
	}

    @Override
    public Loader<StudentCalendarLessonInfo> onCreateLoader(int id, Bundle args) {
        return new LessonExistingVector(getActivity(), mTeachersId);
    }

    @Override
    public void onLoadFinished(Loader<StudentCalendarLessonInfo> loader, StudentCalendarLessonInfo data) {
        mCalendarAdapter.setmLessonMatrix(data.getLessonMatrix());
        mCalendarAdapter.notifyDataSetChanged();
    }


    @Override
    public void onLoaderReset(Loader<StudentCalendarLessonInfo> loader) {

    }
}
