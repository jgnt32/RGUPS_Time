package ru.rgups.time;

import ru.rgups.time.adapters.DrawerListAdapter;
import ru.rgups.time.fragments.SettingFragment;
import ru.rgups.time.fragments.TimeTableFragment;
import ru.rgups.time.fragments.WelcomeActivity;
import ru.rgups.time.interfaces.SettingListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.utils.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements  SettingListener, OnClickListener, OnItemClickListener{

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private TimeTableFragment mTimeTableFragment;
	private SettingFragment mSettingFragment;
	private boolean mReplaceFlag = false;
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
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.frameLayout, mTimeTableFragment, null);
		ft.commit();
	}

	private void openWelcomeActivity(){
		if(PreferenceManager.getInstance().getGroupId() == -1){
			Intent i = new Intent(this, WelcomeActivity.class);
			startActivity(i);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		openWelcomeActivity();
		openTimeTableFragment();
	}

	private void openTimeTableFragment(){
		if(PreferenceManager.getInstance().getGroupId() == -1 &&
				mReplaceFlag == true){
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
		 getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
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
	            invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
	        }

	        @Override
	        public void onDrawerOpened(View drawerView) {
	            invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
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
	}
	
	private void changeFragment(int id){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		switch (id) {
		case DrawerListAdapter.TIME_FRAGMENT:
			ft.replace(R.id.frameLayout, mTimeTableFragment);
			ft.commit();
			break;
			
		case DrawerListAdapter.SETTING_FRAGMENT:
			ft.replace(R.id.frameLayout, mSettingFragment);
			ft.commit();
		default:
			break;
		}
		mDrawerLayout.closeDrawers();
		
	}

	@Override
	public void logOut() {
		PreferenceManager.getInstance().saveGroupId((long) -1);
		this.openWelcomeActivity();
		mReplaceFlag = true;
	}
	
	
	
}
