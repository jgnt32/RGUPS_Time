package ru.rgups.time.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.RealmResults;
import ru.rgups.time.adapters.TeacherLessonListAdapter;
import ru.rgups.time.loaders.TeacherLessonLoader;
import ru.rgups.time.model.TeacherManager;
import ru.rgups.time.model.entity.teachers.TeachersLesson;

public class TeacherLessonListFragment extends LessonListFragment implements LoaderManager.LoaderCallbacks<RealmResults<TeachersLesson>>{
	
	public final static String TEACHER_ARGS = "teacher_args";

	private int mDayNumber;
	private long mTeacherId;
	private Cursor mCursor;

	private TeacherLessonListAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mDayNumber = getArguments().getInt(DAY_ARGS);
        mTeacherId = getArguments().getLong(TEACHER_ARGS);
        RealmResults<TeachersLesson> teachersLessons = TeacherManager.getInstance(getActivity()).getTeachersLessons(mTeacherId, mDayNumber);
        mAdapter = new TeacherLessonListAdapter(getActivity(), teachersLessons, false);
        super.onCreate(savedInstanceState);
		
	}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgress.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mLessonListener.onTeacherClick(id);
	}

	@Override
	protected void setAdapter(ListView list) {
        mListView.setAdapter(mAdapter);
    }

    @Override
    public Loader<RealmResults<TeachersLesson>> onCreateLoader(int i, Bundle bundle) {
        return new TeacherLessonLoader(getActivity(), mDayNumber, mTeacherId);
    }

    @Override
    public void onLoadFinished(Loader<RealmResults<TeachersLesson>> cursorLoader, RealmResults<TeachersLesson> data) {
     /*   mAdapter.updateRealmResults(data);
        mAdapter.notifyDataSetChanged();

        if (mListView.getAdapter() == null) {
        }*/

        RealmResults<TeachersLesson> teachersLessons = TeacherManager.getInstance(getActivity()).getTeachersLessons(mTeacherId, mDayNumber);
        mAdapter.updateRealmResults(teachersLessons);
    }

    @Override
    public void onLoaderReset(Loader<RealmResults<TeachersLesson>> cursorLoader) {

    }
}
