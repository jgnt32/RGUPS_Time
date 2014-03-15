package ru.rgups.time.fragments;

import ru.rgups.time.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TeacherLessonListFragment extends Fragment implements LoaderCallbacks<Cursor>{
	
	public static final String TEACHER_NAME = "teacher_name";
	
	private StickyListHeadersListView mListView;
	private String mTeacherName;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTeacherName = getArguments().getString(TEACHER_NAME);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_fragment, null);
		mListView = (StickyListHeadersListView) v.findViewById(R.id.list_fragment_listview);
		
		return v;

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		
	}
	

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		
	}
	
	private class TeacherLoader extends CursorLoader{

		public TeacherLoader(Context context) {
			super(context);

		}
		
	}

}
