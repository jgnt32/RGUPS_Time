package ru.rgups.time;

import ru.rgups.time.adapters.DrawerListAdapter;
import ru.rgups.time.fragments.SettingFragment;
import ru.rgups.time.fragments.TimeTableFragment;
import ru.rgups.time.utils.PreferenceManager;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements OnClickListener, OnItemClickListener{

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private TimeTableFragment mTimeTableFragment;
	private SettingFragment mSettingFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(this);
		mDrawerList.setAdapter(new DrawerListAdapter(this));

		initActionBar();
		initDrawer();
		initFragmenets();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.frameLayout, mTimeTableFragment, null);
		ft.commit();
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		PreferenceManager.getInstance().saveGroupId("15144");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	
	
}
