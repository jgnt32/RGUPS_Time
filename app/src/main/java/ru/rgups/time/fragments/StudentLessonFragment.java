package ru.rgups.time.fragments;

import ru.rgups.time.adapters.LessonAdapter;
import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.loaders.StudentsLessonLoader;
import ru.rgups.time.model.LessonListElement;

import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class StudentLessonFragment extends LessonListFragment implements LoaderManager.LoaderCallbacks<ArrayList<LessonListElement>> {

	private LessonAdapter mAdapter;
	
	private int mDayNumber;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDayNumber = getArguments().getInt(DAY_ARGS);
		mAdapter = new LessonAdapter(getActivity(), mLessons, LessonManager.getInstance().getTimeStampBySemestrDayNumber(mDayNumber));
	}

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }

    @Override
	protected void setAdapter(ListView list) {
		mListView.setAdapter(mAdapter);
	}

	@Override
	protected void loadLessonsFromDb() {

	}

	@Override
	protected void notifyAdtapter() {
		mAdapter.notifyDataSetChanged();			
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mLessonListener.OnLessonListElementClick(id, LessonManager.getInstance().getTimeStampBySemestrDayNumber(mDayNumber));
	}


    @Override
    public android.support.v4.content.Loader<ArrayList<LessonListElement>> onCreateLoader(int i, Bundle bundle) {
        return new StudentsLessonLoader(getActivity(), mDayNumber);

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<LessonListElement>> arrayListLoader, ArrayList<LessonListElement> lessonListElements) {
        mLessons.clear();
        mLessons.addAll(lessonListElements);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<LessonListElement>> arrayListLoader) {

    }


}
