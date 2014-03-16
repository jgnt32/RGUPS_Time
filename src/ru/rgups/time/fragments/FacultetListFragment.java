package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.BaseDialogFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.FacultetListAdapter;
import ru.rgups.time.interfaces.AuthListener;
import ru.rgups.time.model.entity.Facultet;
import ru.rgups.time.model.entity.FacultetList;
import ru.rgups.time.spice.FacultetListRequest;
import ru.rgups.time.utils.PreferenceManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class FacultetListFragment extends BaseDialogFragment implements OnItemClickListener{
	private ArrayList<Facultet> mFacultetList = new ArrayList<Facultet>();
	private ListView mListView;
	private FacultetListAdapter adapter;
	private AuthListener mAuthListener;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mAuthListener = (AuthListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.facultet_list_fragment, null);
		mListView = (ListView) v.findViewById(R.id.facultetList);
		mListView.setOnItemClickListener(this);
		mListView.setEmptyView(v.findViewById(R.id.facultet_list_empty_view));
		return  v;
	}



	@Override
	public void onResume() {
		mAuthListener.setActionbarTitle(R.string.login_facultet_caption);
		getFacultetList();
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		PreferenceManager.getInstance().saveFacultetId(id);
		mAuthListener.OpenGroupList(id);
	}
	
	
	
	private void getFacultetList(){
	
		this.getSpiceManager().execute(new FacultetListRequest(), new GetFacultetListener());
	}
	
	private class GetFacultetListener implements RequestListener< FacultetList >{

		@Override
		public void onRequestFailure(SpiceException exception) {

			
		}

		@Override
		public void onRequestSuccess(FacultetList list) {
			Log.e("list",""+list.getFacultetList().size());
			mFacultetList = new ArrayList<Facultet>(list.getFacultetList());
			adapter = new FacultetListAdapter(getActivity(), mFacultetList);
			mListView.setAdapter(adapter);
		//	adapter.notifyDataSetChanged();
		}
		
	}

	
	
	

}
