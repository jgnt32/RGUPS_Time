package ru.rgups.time.fragments;

import it.sephiroth.android.library.widget.AbsHListView;
import it.sephiroth.android.library.widget.AbsHListView.OnScrollListener;
import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;
import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.BaseCalendarAdapter;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.utils.CalendarManager;
import ru.rgups.time.views.CalendarHint;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseTameTableFragment extends BaseFragment implements OnScrollListener, OnItemClickListener, android.widget.AdapterView.OnItemClickListener{
	
	private StickyListHeadersListView mLessonList;
	private HListView mCalendarList;
	private LessonListener mLessonListener;
	private BaseCalendarAdapter mCalendarAdapter;
	private int mLastSelectedDatePosition = -1;
	private CalendarHint mCalendarHint;
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.timetable_fragment, null);
		mCalendarList = (HListView) v.findViewById(R.id.calendar_list);
		
		mLessonList = (StickyListHeadersListView) v.findViewById(R.id.lesson_list);
		mLessonList.setOnItemClickListener(this);
		mLessonList.setEmptyView(v.findViewById(R.id.lesson_list_empty_view));
		setLessonAdapter(mLessonList);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		calendarListItemClick();
		return true;
	}
	
	private void calendarListItemClick(){
		mCalendarList.smoothScrollToPosition(CalendarManager.getInstance().getCurrentDayOfTheYear());
		mCalendarList.performItemClick(null, CalendarManager.getInstance().getCurrentDayOfTheYear(), 0);
	}

	protected abstract void setLessonAdapter(StickyListHeadersListView list);
	
	protected abstract void notifyAdapterSetChanged(int day, int weekState);
	protected abstract BaseCalendarAdapter createNewCalendarAdapter();
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
		setLastSelectedDatePosition(position);
		notifyAdapterSetChanged(mCalendarAdapter.getDayNumber(position), mCalendarAdapter.getWeekState(position));
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
}
