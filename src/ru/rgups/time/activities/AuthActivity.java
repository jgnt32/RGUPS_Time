package ru.rgups.time.activities;

import ru.rgups.time.R;
import ru.rgups.time.fragments.FacultetListFragment;
import ru.rgups.time.fragments.GroupListFragment;
import ru.rgups.time.interfaces.WelcomeListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class AuthActivity extends FragmentActivity implements WelcomeListener{

	private FragmentTransaction mFt;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.auth_activity);
	}

	@Override
	public void OpenFacultetList() {
		mFt = getSupportFragmentManager().beginTransaction();
		mFt.replace(R.id.auth_frame_layout, new FacultetListFragment());
		mFt.commit();
	}

	@Override
	public void OpenGroupList(Long facultetId) {
		mFt = getSupportFragmentManager().beginTransaction();
		mFt.replace(R.id.auth_frame_layout, new GroupListFragment());
	}
	
	
}
