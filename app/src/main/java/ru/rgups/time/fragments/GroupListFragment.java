package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.GroupListAdapter;
import ru.rgups.time.interfaces.AuthListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.Group;
import ru.rgups.time.model.entity.GroupList;
import ru.rgups.time.spice.GroupListRequest;
import ru.rgups.time.utils.PreferenceManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;



public class GroupListFragment extends BaseFragment implements OnItemClickListener{
	
	public static final String FUCULTET_ID = "facultet_id";
	
	private ArrayList<Group> mGroupList = new ArrayList<Group>();
	private StickyListHeadersListView mListView;
	private GroupListAdapter mAdapter;
	private Long mFacultetId;
	private AuthListener mAuthListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mAuthListener = (AuthListener) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFacultetId = getArguments().getLong(FUCULTET_ID);
		mGroupList = new ArrayList<Group>();
		getGroupList();
		
		mAdapter = new GroupListAdapter(getActivity(), mGroupList);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mAuthListener.setActionbarTitle(R.string.login_group_caption);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.group_list_fargment, null);
		mListView = new StickyListHeadersListView(getActivity());
        LinearLayout listViewContainer = (LinearLayout) v.findViewById(R.id.group_list_container);
        listViewContainer.addView(mListView);
		mListView.setOnItemClickListener(this);
		mListView.setEmptyView(v.findViewById(R.id.group_list_empty_view));
		mListView.setAdapter(mAdapter);
	    return  v;
	}
	
	private void getGroupList(){
		
		this.getSpiceManager().execute(new GroupListRequest(mFacultetId), new GetGroupListListener());
	}
	
	private class GetGroupListListener implements RequestListener< GroupList >{

		@Override
		public void onRequestFailure(SpiceException exception) {
			exception.printStackTrace();
			
		}

		@Override
		public void onRequestSuccess(GroupList list) {
			mGroupList.clear();
			mGroupList.addAll(DataManager.getInstance().getGroupList(mFacultetId));
			mAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		PreferenceManager.getInstance().saveGroupId(id);
		PreferenceManager.getInstance().saveGroupTitle(mAdapter.getItem(position).getTitle());

		mAuthListener.finishAuthActivity();
	}

}
