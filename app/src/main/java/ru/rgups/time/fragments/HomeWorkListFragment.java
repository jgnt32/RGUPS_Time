package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.HomeWorkCursorAdapter;
import ru.rgups.time.adapters.HomeWorkListAdapter;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.loaders.HomeWorkCursorLoader;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.rest.ApigeeManager;
import ru.rgups.time.rest.RestManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class HomeWorkListFragment extends BaseFragment implements OnItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{

	private StickyListHeadersListView mListView;
	private LessonListener mLessonListener;
	private HomeWorkCursorAdapter mAdpter;

	private View mProgress;
	
	private View mEmptyMessage;
	

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mLessonListener = (LessonListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdpter = new HomeWorkCursorAdapter(getActivity(), null, true);

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.homework_list_fragment, null);
		mListView = new StickyListHeadersListView(getActivity());// because https://github.com/emilsjolander/StickyListHeaders/issues/293
        mListView.setDivider(null);
        mListView.setAreHeadersSticky(false);
        mListView.setSelector(android.R.color.transparent);
        LinearLayout listViewContainer = (LinearLayout) v.findViewById(R.id.sticky_list_view_container);
        listViewContainer.addView(mListView);
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mAdpter);
		mListView.setEmptyView(v.findViewById(R.id.hw_empty_view));
		mEmptyMessage = v.findViewById(R.id.hw_empty_message);
		mProgress = v.findViewById(R.id.hw_empty_progress);
	
		return v;
	}

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }

    @Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	//	mLessonListener.OnLessonListElementClick(mAdpter.getItem(position).getLessonId(), mAdpter.getItem(position).getDate().getTime());
	}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new HomeWorkCursorLoader(getActivity());
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdpter.changeCursor(data);
        mAdpter.notifyDataSetChanged();
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
