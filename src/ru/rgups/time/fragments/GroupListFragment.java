package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.GroupListAdapter;
import ru.rgups.time.model.entity.Group;
import ru.rgups.time.model.entity.GroupList;
import ru.rgups.time.spice.GroupListRequest;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;



public class GroupListFragment extends BaseFragment{
	private ArrayList<Group> mGroupList;
	private StickyListHeadersListView mListView;
	private GroupListAdapter adapter;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGroupList = new ArrayList<Group>();
		getGroupList();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		   View v = getActivity().getLayoutInflater().inflate(R.layout.group_list_fargment, null);
		   mListView = (StickyListHeadersListView) v.findViewById(R.id.groupList);
		
		return  v;
	}
	
	private void getGroupList(){
		
		this.getSpiceManager().execute(new GroupListRequest("1"), new GetGroupListListener());
	}
	
	private class GetGroupListListener implements RequestListener< GroupList >{

		@Override
		public void onRequestFailure(SpiceException exception) {

			
		}

		@Override
		public void onRequestSuccess(GroupList list) {
			Log.e("list",""+list.getGroupList().size());
			mGroupList = new ArrayList<Group>(list.getGroupList());
			adapter = new GroupListAdapter(getActivity(), mGroupList);
	//		mListView.setAdapter(adapter);
		}
		
	}

}
