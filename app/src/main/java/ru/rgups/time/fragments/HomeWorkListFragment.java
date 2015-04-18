package ru.rgups.time.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.HomeWorkCursorAdapter;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.loaders.HomeWorkCursorLoader;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class HomeWorkListFragment extends BaseFragment implements OnItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    public final static long ALL_HOME_WORKS = Long.valueOf(0);
    public final static long COMPITED_HOME_WORKS = Long.valueOf(1);
    public final static long UNCOMPLITED_HOMEWORKS = Long.valueOf(2);

	private StickyListHeadersListView mListView;
	private LessonListener mLessonListener;
	private HomeWorkCursorAdapter mAdpter;

	private View mProgress;



	private View mEmptyMessage;
	private Spinner mSpinner;
    private long mHomeWorkStatus = ALL_HOME_WORKS;


    @Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mLessonListener = (LessonListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdpter = new HomeWorkCursorAdapter(getActivity(), null, true);
        setHasOptionsMenu(true);

	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_work_list, menu);
        mSpinner = (Spinner) MenuItemCompat.getActionView(menu.findItem(R.id.home_work_action_spinner));
        String [] data = {"Все", "Выполненные", "Невыполненные"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.home_work_spinner_drop_down, data);
        adapter.setDropDownViewResource(R.layout.home_work_spinner_drop_down);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(getOnSpinnerItemSelectListener());
    }

    private AdapterView.OnItemSelectedListener getOnSpinnerItemSelectListener() {
        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mHomeWorkStatus = id;
                restartLoader();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
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
        restartLoader();
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }

    @Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		mLessonListener.OnHomeWorkListElementClick(id);
	}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new HomeWorkCursorLoader(getActivity(), mHomeWorkStatus);
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
