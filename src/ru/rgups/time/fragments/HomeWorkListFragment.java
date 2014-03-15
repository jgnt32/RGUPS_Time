package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.adapters.HomeWorkListAdapter;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.model.DataManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeWorkListFragment extends Fragment implements OnItemClickListener{

	private StickyListHeadersListView  mListView;
	private LessonListener mLessonListener;
	private HomeWorkListAdapter mAdpter;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mLessonListener = (LessonListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_fragment, null);
		mAdpter = new HomeWorkListAdapter(getActivity(), DataManager.getInstance().getAllHomeWorks());
		mListView = (StickyListHeadersListView) v.findViewById(R.id.list_fragment_listview);
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mAdpter);
		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		mLessonListener.OnLessonListElementClick(mAdpter.getItem(position).getLessonId(), mAdpter.getItem(position).getDate().getTime());
	}
}
