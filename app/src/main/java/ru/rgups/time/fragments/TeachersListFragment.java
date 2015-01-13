package ru.rgups.time.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FilterQueryProvider;

import java.util.ArrayList;
import java.util.List;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.TeacherListAdapter;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.loaders.RTCursorLoader;
import ru.rgups.time.loaders.TecherListLoader;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.teachers.Teacher;
import ru.rgups.time.rest.RestManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class TeachersListFragment extends BaseFragment implements OnItemClickListener, LoaderCallbacks<List<Teacher>>,
																SearchView.OnQueryTextListener, FilterQueryProvider, MenuItemCompat.OnActionExpandListener{
	private StickyListHeadersListView mListView;
	private TeacherListAdapter mAdapter;
	private RTCursorLoader mLoader;
	private MenuItem mSearchItem;
	private SearchView mSearchView;
	private LessonListener mLessonListener;
	private View mProgressView;

	private View mEmptyMessage;
	
	private Cursor mTeacherListCursor;

    private List<Teacher> teachers = new ArrayList<>();
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mLessonListener = (LessonListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		mAdapter = new TeacherListAdapter(getActivity(), teachers);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.simple_list_fragment, null);
		mListView = new StickyListHeadersListView(getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	/*	mListView.setEmptyView(v.findViewById(R.id.list_fragment_empty_view));
		mProgressView = v.findViewById(R.id.list_fragment_empty_view_progress);
		mEmptyMessage = v.findViewById(R.id.list_fragment_empty_view_message);*/
		return mListView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
        RestManager.getInstance().teacherListRequest(null, getActivity().getApplicationContext());
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.teach, menu);
		mSearchItem = menu.findItem(R.id.action_search);
		mSearchItem.setVisible(true);
	    mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
	    mSearchView.setQueryHint(getString(R.string.search_view_hint));
	    mSearchView.setOnQueryTextListener(this);
	    MenuItemCompat.setOnActionExpandListener(mSearchItem, this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
	    mLessonListener.onTeacherClick(id);
    }

	@Override
	public Loader<List<Teacher>> onCreateLoader(int c, Bundle arg1) {
        String query = null;
        if (mSearchView != null && mSearchView.getQuery() != null) {
            query = mSearchView.getQuery().toString();
        }
        return new TecherListLoader(getActivity(), query);
	}

    @Override
    public void onLoadFinished(Loader<List<Teacher>> loader, List<Teacher> data) {
        Log.e("Teacher list","onLoadFinished "+data);
        teachers.clear();
        teachers.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Teacher>> loader) {

    }



	@Override
	public boolean onQueryTextChange(String value) {
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String value) {
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
		return false;
	}

	@Override
	public Cursor runQuery(CharSequence constraint) {
		return DataManager.getInstance().getFiltredTeachersCursor(constraint.toString());
	}


	@Override
	public boolean onMenuItemActionCollapse(MenuItem arg0) {
		mSearchView.setQuery("", true);
		return true;
	}

	@Override
	public boolean onMenuItemActionExpand(MenuItem arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	
}
