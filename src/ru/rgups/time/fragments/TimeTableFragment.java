package ru.rgups.time.fragments;

import it.sephiroth.android.library.widget.AbsHListView;
import it.sephiroth.android.library.widget.AbsHListView.OnScrollListener;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.Calendar;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.CalendarAdapter;
import ru.rgups.time.adapters.LessonAdapter;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.utils.CalendarManager;
import ru.rgups.time.views.CalendarHint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class TimeTableFragment extends BaseFragment implements OnScrollListener,OnItemClickListener{
	
	public final static String DAY_MONTH_DATE_FORMAT = "d MMMM";
	public static final String DAY_OF_WEEK_DATE_FORMAT = "EEEE";
	
	private HListView mCalendarList;
	private ListView mLessonList;
	private ArrayList<LessonListElement> mLessons = new ArrayList<LessonListElement>();
	private CalendarAdapter mCalendarAdapter;
	private LessonAdapter mLessonAdapter;
	private View mLastSelectedView;
	private int mWeekIndicator;
	private View mEmptyView;


	private CalendarHint mCalendarHint;
	private View mCalendarListelement;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mLessons = DataManager.getInstance().getLessonList(1, 1);
		this.mLessonAdapter = new LessonAdapter(getActivity(), mLessons);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
		menu.findItem(R.id.action_scroll_to_today).setVisible(true);
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
//		mCalendarList.setSelection(CalendarManager.getInstance().getCurrentDatOfTheYear());
//		mCalendarList.performItemClick(null, CalendarManager.getInstance().getCurrentDatOfTheYear(), 0);//		getGroupList();
		DataManager.getInstance().setSpiceManager(getSpiceManager());
		DataManager.getInstance().timeTableRequest(new GetTimeListener());
		
	}

	


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		calendarListItemClick();
		return true;
	}

	private void calendarListItemClick(){
		mCalendarList.smoothScrollToPosition(CalendarManager.getInstance().getCurrentDatOfTheYear());
		mCalendarList.performItemClick(null, CalendarManager.getInstance().getCurrentDatOfTheYear(), 0);
	}

	
	private class GetTimeListener implements RequestListener< LessonList >{
		
		
		@Override
		public void onRequestFailure(SpiceException exception) {
		}

		@Override
		public void onRequestSuccess(LessonList list) {
			Log.e("list",""+list.getDays().size());
			}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.timetable_fragment, null);

		mLessonList = (ListView) v.findViewById(R.id.lesson_list);
		mEmptyView = v.findViewById(R.id.lesson_epty_view);
		mCalendarHint = (CalendarHint) v.findViewById(R.id.calendar_list_hint);
		
		mLessonList.setEmptyView(mEmptyView);
		mLessonList.setAdapter(mLessonAdapter);
		mCalendarList = (HListView) v.findViewById(R.id.calendar_list);
		mCalendarAdapter = new CalendarAdapter(getActivity());
		mCalendarList.setAdapter(mCalendarAdapter);
		mCalendarList.setOnScrollListener(this);
		mCalendarList.setOnItemClickListener(this);
		mCalendarList.setSelector(R.drawable.calendar_selector);
		return v;
	}


	private boolean isOverLine(Calendar c){
		Calendar calendar = c;
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);	
		boolean currentWeekIsParity = (weekOfYear%2)==0;
		Log.e("huy",""+currentWeekIsParity);
		if(mCalendarAdapter.UPARITY_WEEK_IS_OVERLINE){
			if(currentWeekIsParity){
				return false;
			}else{
				return true;
			}
		}else{
			if(currentWeekIsParity){
				return true;
			}else{
				return false;
			}
		}
	}
	
	

	@Override
	public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent,
			View view, int position, long id) {
		mLessons.clear();
		mLessons.addAll(DataManager.getInstance().getLessonList(mCalendarAdapter.getDayNumber(position), mCalendarAdapter.getWeekState(position)));
		mLessonAdapter = new LessonAdapter(getActivity(), mLessons);
		mLessonList.setAdapter(mLessonAdapter);
	}

	
	@Override
	public void onScrollStateChanged(AbsHListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsHListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mCalendarHint.setText(""+CalendarManager.getInstance().getCalendarListMonthTitle(firstVisibleItem));
		
		if(CalendarManager.getInstance().montIsChanged(firstVisibleItem)){
			mCalendarHint.showHint();
		}
		
	}

		
	
}
