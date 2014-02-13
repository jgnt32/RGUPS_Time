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



	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, AuthActivity.class);
		startActivity(i);
	}


}
