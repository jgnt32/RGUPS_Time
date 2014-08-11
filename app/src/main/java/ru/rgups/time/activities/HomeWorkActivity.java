package ru.rgups.time.activities;

import ru.rgups.time.R;
import ru.rgups.time.fragments.HomeWorkEditFragment;
import ru.rgups.time.interfaces.HomeWorkListener;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class HomeWorkActivity extends ActionBarActivity implements HomeWorkListener{

	public static final String LAUNCH_TYPE = "launch_type";
	public static final int EDIT = 0;
	public static final int ADD = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homework_activity);
		initActionBar();
		switch(getIntent().getExtras().getInt(LAUNCH_TYPE)){

		case EDIT:
			EditHomeWork(getIntent().getExtras().getLong(HomeWorkEditFragment.HOMEWORK_ID),
                    getIntent().getExtras().getLong(HomeWorkEditFragment.LESSON_ID),
                    getIntent().getExtras().getLong(HomeWorkEditFragment.DATE));
			break;
		}
	}
	
	private void initActionBar(){
		 getSupportActionBar().setDisplayShowTitleEnabled(true); 
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			this.finisActivity();
			return true;
		default:return super.onOptionsItemSelected(item);
		}
	}
	


	@Override
	public void EditHomeWork(Long hwId, long lessonId, Long date) {
		HomeWorkEditFragment fragment = new HomeWorkEditFragment();
		Bundle args = new Bundle();
		args.putLong(HomeWorkEditFragment.HOMEWORK_ID, hwId);
        args.putLong(HomeWorkEditFragment.LESSON_ID, lessonId);
        args.putLong(HomeWorkEditFragment.DATE, date);
		fragment.setArguments(args);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.homework_activity_frame, fragment);
		ft.commit();		
	}

	@Override
	public void finisActivity() {
		this.finish();
	}
	
}
