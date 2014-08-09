package ru.rgups.time.fragments;

import ru.rgups.time.adapters.TeacherLessonListAdapter;
import ru.rgups.time.loaders.TeacherLessonLoader;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TeacherLessonListFragment extends LessonListFragment implements LoaderManager.LoaderCallbacks<Cursor>{
	
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
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }

    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}

	@Override
	protected void setAdapter(ListView list) {
		list.setAdapter(mAdapter);
	}

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new TeacherLessonLoader(getActivity(), mDayNumber, mTeacherName);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.changeCursor(cursor);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
