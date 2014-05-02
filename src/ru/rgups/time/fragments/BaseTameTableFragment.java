package ru.rgups.time.fragments;

import it.sephiroth.android.library.widget.AbsHListView;
import it.sephiroth.android.library.widget.AbsHListView.OnScrollListener;
import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.lang.reflect.Field;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.BaseCalendarAdapter;
import ru.rgups.time.adapters.LessonListPagerAdapter;
import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.utils.CalendarManager;
import ru.rgups.time.views.CalendarHint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public abstract class BaseTameTableFragment extends BaseFragment implements OnScrollListener, 
OnItemClickListener, android.widget.AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener{
	
	private HListView mCalendarList;
	private LessonListener mLessonListener;
	private BaseCalendarAdapter mCalendarAdapter;
	private int mLastSelectedDatePosition = -1;
	private CalendarHint mCalendarHint;
	private ViewPager mPager;
	private LessonListPagerAdapter mPagerAdapter;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mLessonListener = (LessonListener) activity;

	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCalendarAdapter = createNewCalendarAdapter();
		setRetainInstance(true);
		setHasOptionsMenu(true);
		mPagerAdapter = new LessonListPagerAdapter(getChildFragmentManager(), LessonManager.DAY_COUNT);
		
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.timetable_fragment, null);
		mCalendarList = (HListView) v.findViewById(R.id.calendar_list);
		mPager = (ViewPager) v.findViewById(R.id.lesson_view_pager);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(getLastSelectedDatePosition());
		mPager.setOnPageChangeListener(this);
	
		mCalendarList.setAdapter(mCalendarAdapter);
		mCalendarList.setSelection(getLastSelectedDatePosition());
		mCalendarList.setItemChecked(getLastSelectedDatePosition(), true);
		mCalendarList.setOnItemClickListener(this);
		notifyAdapterSetChanged(mCalendarAdapter.getDayNumber(getLastSelectedDatePosition()), 
				mCalendarAdapter.getWeekState(getLastSelectedDatePosition()));
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
		menu.findItem(R.id.action_scroll_to_today).setVisible(true);
	}
	
	
	@Override
	public void onDetach() {
	    super.onDetach();

	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);

	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		calendarListItemClick();
		return true;
	}
	
	private void calendarListItemClick(){
		int first = mCalendarList.getFirstVisiblePosition();
		int last = mCalendarList.getLastVisiblePosition();
		int current = CalendarManager.getInstance().getCurrentDayOfTheYear();
		if((first > current && first - current >= 10) || 
				(last < current && current - last >= 10)){
		
			mCalendarList.setSelection(CalendarManager.getInstance().getCurrentDayOfTheYear());

		} else {
			mCalendarList.smoothScrollToPosition(CalendarManager.getInstance().getCurrentDayOfTheYear());
			mCalendarList.performItemClick(null, CalendarManager.getInstance().getCurrentDayOfTheYear(), 0);
			
		}
	}

	protected abstract void setLessonAdapter(ListView list);
	
	protected abstract void notifyAdapterSetChanged(int day, int weekState);
	protected abstract BaseCalendarAdapter createNewCalendarAdapter();
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
		mPager.setCurrentItem(position);
		setLastSelectedDatePosition(position);
	}
	
	@Override
	public void onItemClick(android.widget.AdapterView<?> arg0, View v, int position, long id) {
		
		mLessonListener.OnLessonListElementClick(id, mCalendarAdapter.getTimestamp(getLastSelectedDatePosition()));
	}

	protected int getLastSelectedDatePosition() {
		if(mLastSelectedDatePosition == -1){
			return CalendarManager.getInstance().getCurrentDayOfTheYear();
		}else{
			return mLastSelectedDatePosition;			
		}
	}
	
	public void setLastSelectedDatePosition(int mLastSelectedDatePosition) {
		this.mLastSelectedDatePosition = mLastSelectedDatePosition;
	}

	@Override
	public void onScrollStateChanged(AbsHListView view, int scrollState) {
		
	}

	@Override
	public void onScroll(AbsHListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mCalendarHint.setText(""+CalendarManager.getInstance().getCalendarListMonthTitle(firstVisibleItem));
		
		if(CalendarManager.getInstance().montIsChanged(firstVisibleItem)){
			mCalendarHint.showHint();
		}
		
	}
	
	
	protected long getTimeStamp(){
		return mCalendarAdapter.getTimestamp(getLastSelectedDatePosition());
	}
	
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onPageSelected(int position) {
		mCalendarList.setOnItemClickListener(null);
		mCalendarList.setItemChecked(position, true);
		mCalendarList.setOnItemClickListener(this);		
	}
	
}
