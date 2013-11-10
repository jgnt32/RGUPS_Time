package ru.rgups.time;

import ru.rgups.time.fragments.TimeTableFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	/*	Intent i = new Intent(this, WelcomeActivity.class);
		startActivity(i);*/
	//	FacultetListFragment facultetList = new FacultetListFragment();
	//	GroupListFragment list = new GroupListFragment();
		TimeTableFragment list = new TimeTableFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.frameLayout, list, null);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
