package ru.rgups.time.activities;

import ru.rgups.time.R;
import ru.rgups.time.fragments.FacultetListFragment;
import ru.rgups.time.fragments.GroupListFragment;
import ru.rgups.time.fragments.WelcomeActivity;
import ru.rgups.time.interfaces.AuthListener;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class AuthActivity extends ActionBarActivity implements AuthListener{

	private FragmentTransaction mFt;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.auth_activity);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		OpenFacultetList();
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
		GroupListFragment fragment = new GroupListFragment();
		Bundle args = new Bundle();
		args.putLong(GroupListFragment.FUCULTET_ID, facultetId);
		fragment.setArguments(args);
		mFt.replace(R.id.auth_frame_layout, fragment);
		
		mFt.addToBackStack(null);
		mFt.commit();
	}

	@Override
	public void setActionbarTitle(int stringRes) {
		getSupportActionBar().setTitle(stringRes);
	}

	@Override
	public void finishAuthActivity() {
		setResult(RESULT_OK);
		finishActivity(WelcomeActivity.AUTH_REQUEST_CODE);
		AuthActivity.this.finish();
	}
	
	
}
