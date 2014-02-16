package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.activities.AuthActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends FragmentActivity implements OnClickListener{

	public static final int AUTH_REQUEST_CODE = 0;
	
	private FacultetListFragment facultetListFragment;

	private Button mLoginButton;
	
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		facultetListFragment = new FacultetListFragment();
		mLoginButton = (Button) findViewById(R.id.login_button);
		mLoginButton.setOnClickListener(this);
	}

	private void openAuthActivity(){
		Intent i = new Intent(this, AuthActivity.class);
		startActivityForResult(i, AUTH_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
	//	if(requestCode == AUTH_REQUEST_CODE){
			if(requestCode == RESULT_OK){
				finish();
			}
	//	}
	}
	
	@Override
	public void onClick(View v) {
		openAuthActivity();
	}


}
