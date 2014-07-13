package ru.rgups.time.fragments;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.interfaces.SettingListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.rest.ApigeeManager;
import ru.rgups.time.rest.RestManager;
import ru.rgups.time.utils.DialogManager;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SettingFragment extends BaseFragment implements OnClickListener{
	private SettingListener mListener;
	private View mLogoutButton;
	private Button mFullDowloadButton;
	private TextView mGroupTitle;
	private TextView mFacultetTitle;
	private Button mAboutButton;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (SettingListener) activity;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.setting_fragment, null);
		mFullDowloadButton = (Button) v.findViewById(R.id.setting_full_time_button);
		mFullDowloadButton.setOnClickListener(this);
		mLogoutButton = v.findViewById(R.id.setting_logout_button);
		mLogoutButton.setOnClickListener(this);
		mGroupTitle = (TextView) v.findViewById(R.id.setting_group_title);
		mGroupTitle.setText(DataManager.getInstance().getCurrentGroupTitle());
		mFacultetTitle = (TextView) v.findViewById(R.id.setting_facultet_title);
		mFacultetTitle.setText(DataManager.getInstance().getCurrentFacultetTitle());
		mAboutButton = (Button) v.findViewById(R.id.setting_about);
		mAboutButton.setOnClickListener(this);
		return v;
	}

	
	@Override
	public void onResume() {
		super.onResume();
		RestManager.getInstance().setSpiceManager(getSpiceManager());
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.setting_about:
			try {
			
				View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog, null);
				
				TextView version = (TextView) view.findViewById(R.id.about_dialog_version_caption);
				String versionCaption = getResources().getString(R.string.version_caption);
			
				String versionInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
				version.setText(versionCaption.replace("#", versionInfo));
				DialogManager.showNeutralCustomDialog(getActivity(), view);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		
		case R.id.setting_logout_button:
			DialogManager.showPositiveDialog(getActivity(), R.string.logout_dialog_message, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mListener.logOut();						

				}
			});
			
			break;
			
		case R.id.setting_full_time_button:
			DialogManager.showPositiveDialog(getActivity(), R.string.setting_full_download_message, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					RestManager.getInstance().fullTimeRequest(null);
					Crouton.showText(getActivity(), getString(R.string.main_loading_begin), Style.INFO);
				}
			});
			break;
		}
	}

}
