package ru.rgups.time;

import ru.rgups.time.activities.HomeWorkActivity;
import ru.rgups.time.adapters.DrawerListAdapter;
import ru.rgups.time.fragments.HomeWorkEditFragment;
import ru.rgups.time.fragments.HomeWorkFragment;
import ru.rgups.time.fragments.HomeWorkListFragment;
import ru.rgups.time.fragments.SettingFragment;
import ru.rgups.time.fragments.SingleLessonFragment;
import ru.rgups.time.fragments.TeachersListFragment;
import ru.rgups.time.fragments.TimeTableFragment;
import ru.rgups.time.fragments.WelcomeActivity;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.interfaces.SettingListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.utils.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class MainActivity extends BaseDrawerActivity implements  SettingListener, OnClickListener, OnItemClickListener, LessonListener{

	public static final String OVER_DRAWER_TRANSACTION = "over_drawer_transaction";
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private TimeTableFragment mTimeTableFragment;
	private HomeWorkListFragment mHomeWorkListFragment;
	private SettingFragment mSettingFragment;
	private boolean mReplaceFlag = false;
	private TeachersListFragment mTeachersFrament;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(this);
		mDrawerList.setAdapter(new DrawerListAdapter(this));
	//	DataManager.getInstance().getAllLessons();
		initActionBar();
		initDrawer();
		initFragmenets();
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		ft.replace(R.id.frameLayout, mTimeTableFragment, null);
//		ft.commit();
		if(savedInstanceState == null){
	//		openTimeTableFragment();

		}
	}

	private void openWelcomeActivity(){
		if(PreferenceManager.getInstance().getGroupId() == -1){
			Intent i = new Intent(this, WelcomeActivity.class);
			startActivity(i);
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		openWelcomeActivity();

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		DataManager.getInstance().setSpiceManager(getSpiceManager());
	//	DataManager.getInstance().fullTimeRequest(new FullTimeRequestListener());
		}

	private void openTimeTableFragment(){
		if(PreferenceManager.getInstance().getGroupId() != -1){
			changeFragment(DrawerListAdapter.TIME_FRAGMENT);
			mReplaceFlag = false;
		}
	}

	 @Override
	 public void onConfigurationChanged(Configuration newConfig) {
		 super.onConfigurationChanged(newConfig);
	     mDrawerToggle.onConfigurationChanged(newConfig);
	 } 
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 if (mDrawerToggle.onOptionsItemSelected(item)) {
			 return true;
		 }else{
			 return super.onOptionsItemSelected(item);
		 }
	 }
	 
	 
	 private void initActionBar(){
		 getSupportActionBar().setDisplayShowTitleEnabled(true); 
	//	 getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	 }
	
	private void initDrawer(){
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	    
	    if(mDrawerToggle==null){
	        mDrawerToggle = new RTDrawerToggle(this,mDrawerLayout, R.drawable.ic_drawer, R.string.draw_open,
	        		R.string.draw_close);	   
	    }
	    mDrawerLayout.setDrawerListener(mDrawerToggle);	
	 }
	
	private class RTDrawerToggle extends ActionBarDrawerToggle{

		public RTDrawerToggle(Activity activity, DrawerLayout drawerLayout,
				int drawerImageRes, int openDrawerContentDescRes,
				int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
					closeDrawerContentDescRes);
		}
		
		 @Override
	        public void onDrawerClosed(View drawerView) {
//	            invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
	        }

	        @Override
	        public void onDrawerOpened(View drawerView) {
	//            invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
	        }
		
		
	}
	
	 @Override
	 protected void onPostCreate(Bundle savedInstanceState) {
		 super.onPostCreate(savedInstanceState);
	     mDrawerToggle.syncState();
	     
	 }

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		changeFragment(position);
	}

	
	private void initFragmenets(){
		mSettingFragment = new SettingFragment();
		mTimeTableFragment = new TimeTableFragment();
		mTeachersFrament = new TeachersListFragment();
		mHomeWorkListFragment = new HomeWorkListFragment();

	}
	
	private void changeFragment(int id){
		getSupportFragmentManager().popBackStackImmediate(OVER_DRAWER_TRANSACTION, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		switch (id) {
		case DrawerListAdapter.TIME_FRAGMENT:
			ft.replace(R.id.frameLayout, mTimeTableFragment);
			break;
			
		case DrawerListAdapter.TEACHERS_FRAGMENT:
			ft.replace(R.id.frameLayout, mTeachersFrament);
			break;
		
		case DrawerListAdapter.HOME_WORK_LIST_FRAGMENT:
			ft.replace(R.id.frameLayout, mHomeWorkListFragment);
			break;
		
		case DrawerListAdapter.SETTING_FRAGMENT:
			ft.replace(R.id.frameLayout, mSettingFragment);
			break;
			
		default:
			
			break;
			
		}
		ft.commit();

		mDrawerLayout.closeDrawers();
		
	}

	@Override
	public void logOut() {
		PreferenceManager.getInstance().saveGroupId((long) -1);
		this.openWelcomeActivity();
		mReplaceFlag = true;
	}

	@Override
	public void OnLessonListElementClick(long lessonId, Long date) {
		SingleLessonFragment fragment = new SingleLessonFragment();
		Bundle args = new Bundle();
		args.putLong(SingleLessonFragment.LESSON_ID, lessonId);
		args.putLong(SingleLessonFragment.TIMESTAMP, date);
		fragment.setArguments(args);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.addToBackStack(null);
		ft.replace(R.id.frameLayout, fragment, OVER_DRAWER_TRANSACTION);
		ft.commit();
	}

	@Override
	public void OnAddHomeWorkClick(long lessonId, Long date) {
		Intent i = new Intent(this, HomeWorkActivity.class);
		i.putExtra(HomeWorkActivity.LAUNCH_TYPE, HomeWorkActivity.ADD);
		i.putExtra(HomeWorkFragment.DATE, date);
		i.putExtra(HomeWorkFragment.LESSON_ID, lessonId);
		startActivity(i);
	}

	@Override
	public void OnHomeWorkListElementClick(long hwId) {
		Intent i = new Intent(this, HomeWorkActivity.class);
		i.putExtra(HomeWorkActivity.LAUNCH_TYPE, HomeWorkActivity.EDIT);
		i.putExtra(HomeWorkEditFragment.HOMEWORK_ID, hwId);
		startActivity(i);
		
	}
	
	private class FullTimeRequestListener implements RequestListener<Boolean>{

		@Override
		public void onRequestFailure(SpiceException e) {
			e.printStackTrace();
			
		}

		@Override
		public void onRequestSuccess(Boolean response) {
			Log.e(getClass().getSimpleName(), "onRequestSuccess");
		}
		
	}
	
}
