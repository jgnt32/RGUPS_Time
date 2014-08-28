package ru.rgups.time;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import ru.rgups.time.activities.HomeWorkActivity;
import ru.rgups.time.adapters.DrawerListAdapter;
import ru.rgups.time.fragments.ClapFragment;
import ru.rgups.time.fragments.HomeWorkEditFragment;
import ru.rgups.time.fragments.HomeWorkListFragment;
import ru.rgups.time.fragments.SettingFragment;
import ru.rgups.time.fragments.SingleLessonFragment;
import ru.rgups.time.fragments.SingleLessonPageFragment;
import ru.rgups.time.fragments.TeachersListFragment;
import ru.rgups.time.fragments.TeachersTimeTable;
import ru.rgups.time.fragments.StudentTimeTableFragment;
import ru.rgups.time.fragments.WelcomeActivity;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.interfaces.SettingListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.services.LessonNotificationService;
import ru.rgups.time.utils.DialogManager;
import ru.rgups.time.utils.PreferenceManager;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
    public static final String NOTIFICATION_LESSON_ID = "notofoaction_lesson_id";
    public static final String NOTIFICATION_LESSON_DATE = "notification_lesson_date";

    private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private StudentTimeTableFragment mTimeTableFragment;
	private HomeWorkListFragment mHomeWorkListFragment;
	private SettingFragment mSettingFragment;
	private boolean mReplaceFlag = true;
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
        DataManager.getInstance().writeToSD(this);
        startNotificationService();
        if(savedInstanceState == null){
            openTimeTableFragment();
        }
        handleOnNotificationClick(getIntent().getExtras());

	}


    private void handleOnNotificationClick(Bundle extra){
        if(extra != null){

            SingleLessonPageFragment fragment = new SingleLessonPageFragment();
            Bundle args = new Bundle();
            args.putLong(SingleLessonFragment.LESSON_ID, extra.getLong(NOTIFICATION_LESSON_ID));
            args.putLong(SingleLessonFragment.TIMESTAMP, extra.getLong(NOTIFICATION_LESSON_DATE));
            fragment.setArguments(args);
            openFragment(fragment, null);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleOnNotificationClick(intent.getExtras());
    }

    public void startNotificationService(){
        if(PreferenceManager.getInstance().statusBarNotificationIsEnabled()){
            //Start Service service to handle data refresh
            Intent serviceIntent = new Intent(this, LessonNotificationService.class);

            //Schedule additional service calls using alarm manager.
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
            PendingIntent pi = PendingIntent.getService(this, 0, serviceIntent, 0);

            //Retrieve time interval from settings (a good practice to let users set the interval).
            alarmManager.cancel(pi);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 10000  , pi);

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
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void enableLessonNotification(boolean value) {
        PreferenceManager.getInstance().setStatusBarNotificationEnebled(value);
        if(value){
            startNotificationService();
        } else {
            stopNotificationService();
        }
    }

    private void stopNotificationService() {
        Intent serviceIntent = new Intent(this, LessonNotificationService.class);
        stopService(serviceIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
	public void OnLessonListElementClick(long lessonId, Long date) {
        SingleLessonPageFragment fragment = new SingleLessonPageFragment();
		Bundle args = new Bundle();
		args.putLong(SingleLessonFragment.LESSON_ID, lessonId);
		args.putLong(SingleLessonFragment.TIMESTAMP, date);
		fragment.setArguments(args);
        openFragment(fragment, OVER_DRAWER_TRANSACTION);
	}

    private void openFragment(SingleLessonPageFragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, fragment, tag);
        ft.addToBackStack(null);

        ft.commit();
    }

    @Override
	public void OnAddHomeWorkClick(long lessonId, Long date) {
		Intent i = new Intent(this, HomeWorkActivity.class);
		i.putExtra(HomeWorkActivity.LAUNCH_TYPE, HomeWorkActivity.EDIT);
		i.putExtra(HomeWorkEditFragment.DATE, date);
		i.putExtra(HomeWorkEditFragment.LESSON_ID, lessonId);
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
