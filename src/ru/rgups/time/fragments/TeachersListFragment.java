package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.adapters.TeacherCursorAdapter;
import ru.rgups.time.loaders.RTCursorLoader;
import ru.rgups.time.model.DataManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

public class TeachersListFragment extends Fragment implements OnItemClickListener, LoaderCallbacks<Cursor>, 
																SearchView.OnQueryTextListener, FilterQueryProvider{
	private ListView mListView;
	private TeacherCursorAdapter mAdapter;
	private RTCursorLoader mLoader;
	private MenuItem mSearchItem;
	private SearchView mSearchView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);

		mAdapter = new TeacherCursorAdapter(getActivity(), null, true);
		mAdapter.setFilterQueryProvider(this);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mLoader.forceLoad();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mLoader.stopLoading();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.simple_list_fragment, null);
		mListView = (ListView) v.findViewById(R.id.list_fragment_listview);
		mListView.setAdapter(mAdapter);
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.teach, menu);
		
		mSearchItem = menu.findItem(R.id.action_search);
		mSearchItem.setVisible(true);
	    mSearchView = (SearchView) mSearchItem.getActionView();
	    mSearchView.setQueryHint(getString(R.string.search_view_hint));
	    mSearchView.setOnQueryTextListener(this);	
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int c, Bundle arg1) {
		return mLoader = new RTCursorLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		mAdapter.changeCursor(c);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		
	}

	@Override
	public boolean onQueryTextChange(String value) {
		mAdapter.getFilter().filter(value);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String value) {
		return false;
	}

	@Override
	public Cursor runQuery(CharSequence constraint) {
		return DataManager.getInstance().getFiltredTeachersCursor(constraint.toString());
	}

}
