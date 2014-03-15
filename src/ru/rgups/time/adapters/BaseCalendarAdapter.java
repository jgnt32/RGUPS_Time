package ru.rgups.time.adapters;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.rgups.time.R;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;
import ru.rgups.time.utils.ConstUtils;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BaseCalendarAdapter extends BaseAdapter{
public static final String HW_DATE_FORMAT = "dd-MM-yyyy";
	
	public final static int CURRENS_SEMESTR = spotSemestr();//

	public final static int DAY_COUNT = getCorrectDayCount();
	
	public final static int DAY_OFFSET = getDayOffset(); 
	
	public final static boolean UPARITY_WEEK_IS_OVERLINE = getPointOfReference();
	
	public final static String DAY_OF_WEEK_FORMAT = "EE";
	private GregorianCalendar mCalendar;
	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	private View mView;
	private int mSelectedItem;
	protected int mBlueColor;
	protected int mRedColor;
	protected Context mContext;
	private View leftIndicator;

	private View rightIndicator;

	private View topIndicator;

	private View bootomIndicator;
	
	private View mHWIndicator;

	private int mLastSelected = -1;

	private GregorianCalendar mTimestampCalendar;
	
	private Color mBlue;

	
	
	public BaseCalendarAdapter(Context context){
		mContext = context;
		mRedColor = context.getResources().getColor(R.color.red);
		mBlueColor = context.getResources().getColor(R.color.theme_blue);
		mCalendar = new GregorianCalendar();
		mCalendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
		mCalendar.setMinimalDaysInFirstWeek(4);
		mCalendar.setTime(Calendar.getInstance().getTime());
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTimestampCalendar = new GregorianCalendar();
		
		mTimestampCalendar.setTime(Calendar.getInstance().getTime());
		mTimestampCalendar.set(GregorianCalendar.HOUR, 0);
		mTimestampCalendar.set(GregorianCalendar.MINUTE, 0);
		mTimestampCalendar.set(GregorianCalendar.SECOND, 0);
		mTimestampCalendar.set(GregorianCalendar.MILLISECOND, 0);		
	}
	
	@Override
	public int getCount() {
		return DAY_COUNT;
	}
	
	public void setSelectedItem(int selecteNumber){
		this.mSelectedItem = selecteNumber;
	}
	
	@Override
	public GregorianCalendar getItem(int position) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
		calendar.setTime(Calendar.getInstance().getTime());
		calendar.set(Calendar.DAY_OF_YEAR, (int) getItemId(position));
		return calendar;
	}
	

	@Override
	public long getItemId(int position) {
		return position+DAY_OFFSET;
	}
	
	public long getTimestamp(int position){
		mTimestampCalendar.set(GregorianCalendar.DAY_OF_YEAR, (int) getItemId(position));
		
		return mTimestampCalendar.getTimeInMillis();
	}
	
	public String getDateInString(int position){
		mTimestampCalendar.set(GregorianCalendar.DAY_OF_YEAR, (int) getItemId(position));
		return DateFormat.format(HW_DATE_FORMAT, mTimestampCalendar.getTime()).toString();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
//		mView = convertView;
//		if(mView == null){
			mView = mInflater.inflate(R.layout.calendar_element, null);
/*			mHolder = new ViewHolder(mView);
			mView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) mView.getTag();
		}*/
			
		  TextView text = (TextView) mView.findViewById(R.id.calendar_element_text);
		  TextView dayOfWeek = (TextView) mView.findViewById(R.id.calendar_element_day_of_week);
		  leftIndicator = mView.findViewById(R.id.calendar_left_indicator);
		  rightIndicator = mView.findViewById(R.id.calendar_right_indicator);
		  topIndicator = mView.findViewById(R.id.calendar_top_indicator);
		  bootomIndicator = mView.findViewById(R.id.calendar_bottom_indicator);
		  mHWIndicator = mView.findViewById(R.id.calendar_element_homework_indicator);
		  TextView hwCount = (TextView) mView.findViewById(R.id.lesson_indicator_text);
		  mCalendar.set(Calendar.DAY_OF_YEAR, position+DAY_OFFSET);
		  text.setText(Integer.toString(mCalendar.get(Calendar.DAY_OF_MONTH)));
		  dayOfWeek.setText(DateFormat.format(DAY_OF_WEEK_FORMAT, mCalendar.getTime()).toString().toUpperCase());
		  if(this.isOverLine(mCalendar.getTime())){
			  bootomIndicator.setVisibility(View.VISIBLE);
			  topIndicator.setVisibility(View.INVISIBLE);
		  }else{
			  bootomIndicator.setVisibility(View.INVISIBLE);
			  topIndicator.setVisibility(View.VISIBLE);
		  }
		  
		  if(mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
			  leftIndicator.setVisibility(View.VISIBLE);
			  rightIndicator.setVisibility(View.INVISIBLE);

		  }else{
			  if(mCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				  rightIndicator.setVisibility(View.VISIBLE);
				  leftIndicator.setVisibility(View.INVISIBLE);
			  }else{
				  rightIndicator.setVisibility(View.INVISIBLE);
				  leftIndicator.setVisibility(View.INVISIBLE);

			  }
		  }
		
		return mView;
	}
	
	
	private static int getCorrectDayCount(){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(Calendar.getInstance().getTime());
		if(CURRENS_SEMESTR == ConstUtils.FIRST_SEMESTR){
			return 122;						//первый семестр
		}else{
			if(calendar.getMaximum(Calendar.DAY_OF_YEAR)==355){ //второй семестр
				return 212;
			}else{
				return 213;//високосний год
			}
		}
		
	}
	
	public static int spotSemestr(){
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if(month>=Calendar.SEPTEMBER){
			return ConstUtils.FIRST_SEMESTR;
		}else{
			return ConstUtils.SECOND_SEMESTR;
		}

	}
	
	private static int getDayOffset(){
		if(CURRENS_SEMESTR == ConstUtils.FIRST_SEMESTR){
			GregorianCalendar calendar = new GregorianCalendar();
					
			calendar.setTime(Calendar.getInstance().getTime());
			int dayOffset = calendar.getMaximum(Calendar.DAY_OF_YEAR)-DAY_COUNT;
			return dayOffset;
		}else{
			return 1;  //because first position = 0
		}
	
	}
	
	private static boolean getPointOfReference(){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(Calendar.getInstance().getTime());
		calendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		int firstWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		
		if(calendar.get(Calendar.DAY_OF_WEEK)!=Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY){
			if(weekIsParity(firstWeekOfYear)){
				return false;
			}else{
				return true;
			}
						
		}else{
			if(weekIsParity(firstWeekOfYear)){
				return true;
			}else{
				return false;
			}
		}
		
	}
	
	public static boolean weekIsParity(int weekNumber){
		if(weekNumber%2==0){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isOverLine(final Date date){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTime(date);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);	
		boolean currentWeekIsParity = (weekOfYear%2)==0;
		Log.e("huy",""+currentWeekIsParity);
		if(UPARITY_WEEK_IS_OVERLINE){
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
	
	
	public int getWeekState(int position){		
		if(this.isOverLine(getItem(position).getTime())){
			return OverLine.WEEK_STATE;
		}else{
			return UnderLine.WEEK_STATE;
		}
	}
	
	public int getDayNumber(int position){
		Log.e("getDayNumber",""+ getItem(position).getTime().toString());
		if(getItem(position).get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY){
			return 7;
		}else{
			return getItem(position).get(GregorianCalendar.DAY_OF_WEEK)-1;
		}
	}
	
	private class ViewHolder{
		private View leftIndicator;
		private View rightIndicator;
		private View topIndicator;
		private View bootomIndicator;
		private TextView dayOfWeek;
		private TextView text;
		public ViewHolder(View v) {
			text = (TextView) v.findViewById(R.id.calendar_element_text);
			dayOfWeek = (TextView) v.findViewById(R.id.calendar_element_day_of_week);
			leftIndicator = v.findViewById(R.id.calendar_left_indicator);
			rightIndicator = v.findViewById(R.id.calendar_right_indicator);
			topIndicator = v.findViewById(R.id.calendar_top_indicator);
			bootomIndicator = v.findViewById(R.id.calendar_bottom_indicator);
		}
	}
	
	public void setSelected(int last){
		mLastSelected  = last;
	}
}
