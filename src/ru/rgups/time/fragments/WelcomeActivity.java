package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.interfaces.WelcomeListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends FragmentActivity implements OnClickListener,WelcomeListener{

	private FacultetListFragment facultetListFragment;
	private TextView mFacultetTextView;
	private TextView mGroupTextView;
	private Button mLoginButton;
	
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_fragment);
		facultetListFragment = new FacultetListFragment();
		mFacultetTextView = (TextView) findViewById(R.id.facultet_select);
		mGroupTextView = (TextView) findViewById(R.id.group_select);
		mLoginButton = (Button) findViewById(R.id.login_button);
		mFacultetTextView.setOnClickListener(this);
		mGroupTextView.setOnClickListener(this);
		mLoginButton.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.facultet_select:
			facultetListFragment.show(getSupportFragmentManager(), null);
			break;
		case R.id.group_select:
			
			break;
		case R.id.login_button:
			
			break;
		}
	}



	@Override
	public void OnFacultetClick(String facultetTitle) {
		
	}



	@Override
	public void OnGroupClick(String groupTitle) {
		
	}	

}
