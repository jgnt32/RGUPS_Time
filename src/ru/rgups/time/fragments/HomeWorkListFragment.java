package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.adapters.HomeWorkListAdapter;
import ru.rgups.time.model.DataManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HomeWorkListFragment extends Fragment{

	private StickyListHeadersListView  mListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_fragment, null);
		mListView = (StickyListHeadersListView) v.findViewById(R.id.list_fragment_listview);
		mListView.setAdapter(new HomeWorkListAdapter(getActivity(), DataManager.getInstance().getAllHomeWorks()));
		return v;
	}
}
