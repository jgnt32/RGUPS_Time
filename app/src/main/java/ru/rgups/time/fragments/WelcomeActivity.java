package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.activities.AuthActivity;
import ru.rgups.time.utils.Blur;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends FragmentActivity implements OnClickListener{

	public static final int AUTH_REQUEST_CODE = 0;
	
	private FacultetListFragment facultetListFragment;

	private Button mLoginButton;
	private ImageView mLoginPoster;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
        mLoginPoster = (ImageView) findViewById(R.id.login_poster);
        BitmapDrawable image = (BitmapDrawable) getResources().getDrawable(R.drawable.poster);
        mLoginPoster.setImageBitmap(Blur.fastblur(image.getBitmap(), 20));
		facultetListFragment = new FacultetListFragment();
		mLoginButton = (Button) findViewById(R.id.login_button);
		mLoginButton.setOnClickListener(this);
	}

	private void openAuthActivity(){
		Intent i = new Intent(this, AuthActivity.class);
		startActivityForResult(i, AUTH_REQUEST_CODE);
	}
	
	@Override
	public void onBackPressed() {
		if(getSupportFragmentManager().getBackStackEntryCount() == 0){
			Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_HOME);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(intent);
		}else{
			super.onBackPressed();
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
	//	if(requestCode == AUTH_REQUEST_CODE){
			if(resultCode == RESULT_OK){
				finish();
			}
	//	}
	}
	
	@Override
	public void onClick(View v) {
		openAuthActivity();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("welcome activity","onDestroy");
	}


}
