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
import ru.rgups.time.model.HelperManager;
import ru.rgups.time.model.entity.Day;
import ru.rgups.time.model.entity.Lesson;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.spice.TimeTableRequest;
import ru.rgups.time.utils.CalendarManager;
import ru.rgups.time.views.CalendarHint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class TimeTableFragment extends BaseFragment implements OnScrollListener,OnItemClickListener{
	
	public final static String DAY_MONTH_DATE_FORMAT = "d MMMM";
	public static final String DAY_OF_WEEK_DATE_FORMAT = "EEEE";
	
	private HListView mCalendarList;
	private ListView mLessonList;
	private ArrayList<Lesson> mLessons = new ArrayList<Lesson>();
	private CalendarAdapter mCalendarAdapter;
	private LessonAdapter mLessonAdapter;
	private View mLastSelectedView;
	private int mWeekIndicator;
	private View mEmptyView;


	private CalendarHint mCalendarHint;
	private View mCalendarListelement;
	
	
	private void getGroupList(){	
		this.getSpiceManager().execute(
				new TimeTableRequest("15144"), 
				0, 
				DurationInMillis.ALWAYS_EXPIRED, 
				new GetTimeListener());
	}
	
	
	private class GetTimeListener implements RequestListener< LessonList >{
		
		
		@Override
		public void onRequestFailure(SpiceException exception) {
		}

		@Override
		public void onRequestSuccess(LessonList list) {
			Log.e("list",""+list.getDays().size());
			ArrayList<Lesson> lessons = new ArrayList<Lesson>();
			lessons.addAll(lessons);
			Cursor c = HelperManager.getHelper().getReadableDatabase().rawQuery("SELECT * FROM "+Lesson.TABLE_NAME, new String[]{});
			Log.e("fuack e",""+c.getCount());
			
			while(c.moveToNext()){
				Log.e("huy",""+c.getString(c.getColumnIndex(Lesson.DAY_ID))+" "+c.getString(c.getColumnIndex(Lesson.NUMBER))+" id = "+c.getString(c.getColumnIndex(Lesson.ID)));
			}

			/*		for(Day day:list.getDays()){
		//		Log.e("day number",""+day.getNumber());
				for(Lesson lesson:day.getLessons()){
			//		Log.e("lesson number",""+lesson.getNumber());
					ArrayList<LessonInformation> tempList = new ArrayList<LessonInformation>();
					
					if(lesson.getDoubleLine()!=null){
						tempList.addAll(lesson.getDoubleLine());
					}

					if(lesson.getOverLine()!=null){
						tempList.addAll(lesson.getOverLine());
					}
					if(lesson.getUnderLine()!=null){
						tempList.addAll(lesson.getUnderLine());
					}
					
					lesson.getInfromation().addAll(tempList);
			//Ы		Log.e("inf count",""+lesson.getInfromation().size());
					lesson.setDayNumber(day.getNumber());
				}
				lessons.addAll(day.getLessons());
			}
		//	Log.e("all lessons",""+lessons.size());
			
			LessonAdapter adapter = new LessonAdapter(getActivity(),lessons);
			mList.setAdapter(adapter);*/
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		this.mLessonAdapter = new LessonAdapter(getActivity(), mLessons,0);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
		menu.findItem(R.id.action_scroll_to_today).setVisible(true);
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
				Log.e("huy","��� ������");
				return false;
			
			}else{
				
				Log.e("pizduy","��� ������");
				return true;
			}
			
		}else{
			if(currentWeekIsParity){
				Log.e("pizduy","��� ������");
				return true;
			}else{
				Log.e("huy","��� ������");
				return false;
			}
			
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		mCalendarList.setSelection(CalendarManager.getInstance().getCurrentDatOfTheYear());
		mCalendarList.performItemClick(null, CalendarManager.getInstance().getCurrentDatOfTheYear(), 0);//		getGroupList();
	//	Cursor c = HelperManager.getHelper().getReadableDatabase().rawQuery("SELECT * FROM "+LessonList.TABLE_NAME, new String[]{});
	//	Log.e("fuack e",""+c.getCount());
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



/*	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position,	long id) {
		if(mCalendarListelement != null){
			mCalendarListelement.setSelected(false);
		}
		v.setSelected(true);
		mCalendarAdapter.setSelected(position);

		mCalendarListelement = v;
		Calendar c = (Calendar) mCalendarAdapter.getItem(position);
		c.set(Calendar.DAY_OF_YEAR, (int) id);
		Log.e("mAdapter.getItem(position)",""+c.getTime()+"; ������ ���� "+c.get(Calendar.WEEK_OF_YEAR)+";������  "+c.get(Calendar.WEEK_OF_YEAR)%2);
		mWeekIndicator = 0;

		if(isOverLine(c)){
			mWeekIndicator = LessonAdapter.OVER_LINE;
//				Log.e("������","��� ������");
		}else{
			mWeekIndicator = LessonAdapter.UNDER_LINE;

	//		Log.e("������","��� ������");
		}
		try {
			Log.e("mAdapter.getItem(position)",""+c.getTime()+"; ������ ���� "+c.get(Calendar.WEEK_OF_YEAR)+";������  "+c.get(Calendar.WEEK_OF_YEAR)%2);
			Log.e("Day OF calendar",""+c.get(Calendar.DAY_OF_WEEK));

			Day d = HelperManager.getDayDAO().queryForId(c.get(Calendar.DAY_OF_WEEK)-1);
		//	Log.e("Day from db",""+d.getNumber());
			mLessons  = new ArrayList<Lesson>(d.getLessons());
			mLessonList.setAdapter(new LessonAdapter(getActivity(),mLessons,mWeekIndicator));
	
		} catch (Exception e) {
			e.printStackTrace();
			mLessons  = new ArrayList<Lesson>();
			mLessonList.setAdapter(new LessonAdapter(getActivity(),mLessons,mWeekIndicator));

		}
		
		/*	v.setSelected(true);
		mAdapter.setSelectedItem(position);
		mLastSelectedView = v;
		mAdapter.notifyDataSetChanged();
	}
*/
	@Override
	public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent,
			View view, int position, long id) {
		
		Calendar c = (Calendar) mCalendarAdapter.getItem(position);
		c.set(Calendar.DAY_OF_YEAR, (int) id);
		Log.e("mAdapter.getItem(position)",""+c.getTime()+"; ������ ���� "+c.get(Calendar.WEEK_OF_YEAR)+";������  "+c.get(Calendar.WEEK_OF_YEAR)%2);
		mWeekIndicator = 0;

		if(isOverLine(c)){
			mWeekIndicator = LessonAdapter.OVER_LINE;
//				Log.e("������","��� ������");
		}else{
			mWeekIndicator = LessonAdapter.UNDER_LINE;

	//		Log.e("������","��� ������");
		}
		try {
			Log.e("mAdapter.getItem(position)",""+c.getTime()+"; ������ ���� "+c.get(Calendar.WEEK_OF_YEAR)+";������  "+c.get(Calendar.WEEK_OF_YEAR)%2);
			Log.e("Day OF calendar",""+c.get(Calendar.DAY_OF_WEEK));

			Day d = HelperManager.getDayDAO().queryForId(c.get(Calendar.DAY_OF_WEEK)-1);
		//	Log.e("Day from db",""+d.getNumber());
			mLessons  = new ArrayList<Lesson>(d.getLessons());
			mLessonList.setAdapter(new LessonAdapter(getActivity(),mLessons,mWeekIndicator));
	
		} catch (Exception e) {
			e.printStackTrace();
			mLessons  = new ArrayList<Lesson>();
			mLessonList.setAdapter(new LessonAdapter(getActivity(),mLessons,mWeekIndicator));
		}
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
