package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.interfaces.SettingListener;
import ru.rgups.time.utils.PreferenceManager;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingFragment extends Fragment implements OnClickListener{
	private SettingListener mListener;
	private View mLogoutButton;
	private TextView mGroupTitle;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (SettingListener) activity;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.setting_fragment, null);
		mLogoutButton = v.findViewById(R.id.setting_logout_button);
		mLogoutButton.setOnClickListener(this);
		mGroupTitle = (TextView) v.findViewById(R.id.setting_group_title);
		if(PreferenceManager.getInstance().getGroupTitle() != null){
			mGroupTitle.setText(PreferenceManager.getInstance().getGroupTitle());
		}
		return v;
	}



	@Override
	public void onClick(View v) {
		mListener.logOut();		
	}

}
