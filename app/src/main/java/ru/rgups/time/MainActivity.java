package ru.rgups.time;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import ru.rgups.time.activities.HomeWorkActivity;
import ru.rgups.time.adapters.DrawerListAdapter;
import ru.rgups.time.fragments.ClapFragment;
import ru.rgups.time.fragments.HomeWorkEditFragment;
import ru.rgups.time.fragments.HomeWorkFragment;
import ru.rgups.time.fragments.HomeWorkListFragment;
import ru.rgups.time.fragments.SettingFragment;
import ru.rgups.time.fragments.SingleLessonFragment;
import ru.rgups.time.fragments.TeachersListFragment;
import ru.rgups.time.fragments.TeachersTimeTable;
import ru.rgups.time.fragments.StudentTimeTableFragment;
import ru.rgups.time.fragments.WelcomeActivity;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.interfaces.SettingListener;
import ru.rgups.time.rest.RestManager;
import ru.rgups.time.utils.DialogManager;
import ru.rgups.time.utils.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class MainActivity extends BaseDrawerActivity implements  SettingListener, OnClickListener, OnItemClickListener, LessonListener{

	public static final String OVER_DRAWER_TRANSACTION = "over_drawer_transaction";
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private StudentTimeTableFragment mTimeTableFragment;
	private HomeWorkListFragment mHomeWorkListFragment;
	private SettingFragment mSettingFragment;
	private boolean mReplaceFlag = false;
	private TeachersListFragment mTeachersFrament;
	private ProgressDialog mProgressDialog;
	private MenuDrawer mDrawer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
//		Crashlytics.start(this);
		mDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, Position.LEFT, MenuDrawer.MENU_DRAG_WINDOW);
        mDrawer.setContentView(R.layout.activity_main);
        mDrawer.setMenuView(R.layout.menu_drawer);		
				
        findViewById(R.id.drawer_homework).setOnClickListener(this);
        findViewById(R.id.drawer_teachers).setOnClickListener(this);
        findViewById(R.id.drawer_timetable).setOnClickListener(this);
        findViewById(R.id.drawer_setting).setOnClickListener(this);
        openWelcomeActivity();
		
		mProgressDialog = DialogManager.getNewProgressDialog(this, R.string.progress_message);
/*		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(this);
		mDrawerList.setAdapter(new DrawerListAdapter(this));*/
		initActionBar();
//		initDrawer();
		initFragmenets();
		if(savedInstanceState == null && PreferenceManager.getInstance().isFacultetsTimeDowloaded()){
			openTimeTableFragment();

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

	}

	
	@Override
	protected void onResume() {
		super.onResume();
		RestManager.getInstance().setSpiceManager(getSpiceManager());
	/*	if(!PreferenceManager.getInstance().isFacultetsTimeDowloaded() && PreferenceManager.getInstance().getFacultetId() != -1){
			RestManager.getInstance().exucuteFacultetRequest(new FacultetTimeRequestListener());
			mProgressDialog.show();
		}else{
			if(mReplaceFlag){*/
				openTimeTableFragment();
//			}
//		}
	}

	private void openTimeTableFragment(){
		if(PreferenceManager.getInstance().getGroupId() != -1){
			changeFragment(DrawerListAdapter.TIME_FRAGMENT);
			mReplaceFlag = false;
		}
	}
	
	
	private void showClapFragment(){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.frameLayout, new ClapFragment());
		ft.commit();
	
	}


	 @Override
	 public void onConfigurationChanged(Configuration newConfig) {
		 super.onConfigurationChanged(newConfig);
	 //    mDrawerToggle.onConfigurationChanged(newConfig);
	 } 
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
         case android.R.id.home:
             mDrawer.toggleMenu();
             return true;
     }

     return super.onOptionsItemSelected(item);
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
	//            invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
	        }

	        @Override
	        public void onDrawerOpened(View drawerView) {
//	            invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
	        }
		
		
	}
	
	 @Override
	 protected void onPostCreate(Bundle savedInstanceState) {
		 super.onPostCreate(savedInstanceState);
	//     mDrawerToggle.syncState();
	     
	 }

	 @Override
	 protected void onDestroy() {
		 super.onDestroy();
		 Crouton.cancelAllCroutons();
	 }
	 
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.drawer_timetable:
			changeFragment(DrawerListAdapter.TIME_FRAGMENT);
			break;
			
		case R.id.drawer_homework:
			changeFragment(DrawerListAdapter.HOME_WORK_LIST_FRAGMENT);

			break;
					
		case R.id.drawer_teachers:
			changeFragment(DrawerListAdapter.TEACHERS_FRAGMENT);

			break;
			
		case R.id.drawer_setting:
			changeFragment(DrawerListAdapter.SETTING_FRAGMENT);

			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		changeFragment(position);
	}

	
	private void initFragmenets(){
		mSettingFragment = new SettingFragment();
		mTimeTableFragment = new StudentTimeTableFragment();
		mTeachersFrament = new TeachersListFragment();
		mHomeWorkListFragment = new HomeWorkListFragment();

	}
	
	private void changeFragment(int id){
		getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
		mDrawer.closeMenu();
//		mDrawerLayout.closeDrawers();
		
	}
	
	

	@Override
	public void logOut() {
		showClapFragment();
		
		PreferenceManager.getInstance().saveGroupId((long) -1);
		PreferenceManager.getInstance().saveFacultetId((long) -1);
		PreferenceManager.getInstance().setFucultetsTimeDownloaded(false);
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
		ft.replace(R.id.frameLayout, fragment, OVER_DRAWER_TRANSACTION);
		ft.addToBackStack(null);

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
	
	private class FacultetTimeRequestListener implements RequestListener<Boolean>{

		@Override
		public void onRequestFailure(SpiceException e) {
			e.printStackTrace();
			mProgressDialog.cancel();
			
		}

		@Override
		public void onRequestSuccess(Boolean response) {
			openTimeTableFragment();
			PreferenceManager.getInstance().setFucultetsTimeDownloaded(true);
			mProgressDialog.cancel();

		}
		
	}

	@Override
	public void onTeacherClick(String teachersName) {
		Bundle args = new Bundle();
		args.putString(TeachersTimeTable.TEACHERS_NAME, teachersName);
		TeachersTimeTable fragment = new TeachersTimeTable();
		fragment.setArguments(args);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.addToBackStack(null);
		ft.replace(R.id.frameLayout, fragment);
		ft.commit();
		
	}
	
}
