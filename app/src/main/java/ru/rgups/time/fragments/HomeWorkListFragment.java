package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.HomeWorkCursorAdapter;
import ru.rgups.time.adapters.HomeWorkListAdapter;
import ru.rgups.time.interfaces.LessonListener;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HomeWorkListFragment extends BaseFragment implements OnItemClickListener{

	private ListView  mListView;
	private LessonListener mLessonListener;
	private HomeWorkCursorAdapter mAdpter;
	private HomeWorkAsyncTask mAsyncTask;
	
	private View mProgress;
	
	private View mEmptyMessage;
	
	private Cursor mCursor;

	
	private ArrayList<HomeWork> mHomeWorks = new ArrayList<HomeWork>();
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mLessonListener = (LessonListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdpter = new HomeWorkCursorAdapter(getActivity(), null, true);
		mAsyncTask = new HomeWorkAsyncTask();
		mAsyncTask.execute();
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.homework_list_fragment, null);
		mListView = (ListView) v.findViewById(R.id.homework_list_fragment_listview);
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
        ApigeeManager.getInstance().fetchHomeWorks();
    }

    @Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	//	mLessonListener.OnLessonListElementClick(mAdpter.getItem(position).getLessonId(), mAdpter.getItem(position).getDate().getTime());
	}
	
	private class HomeWorkAsyncTask extends AsyncTask<Void, Void, Void>{


		@Override
		protected Void doInBackground(Void... params) {
			mCursor = DataManager.getInstance().getHomeWorks();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(isAdded()){
				mAdpter.changeCursor(mCursor);
			//	mAdpter.notifyDataSetChanged();
				mEmptyMessage.setVisibility(View.VISIBLE);
				mProgress.setVisibility(View.GONE);
			}
		}
		
	}
}
