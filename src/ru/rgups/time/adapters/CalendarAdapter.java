package ru.rgups.time.adapters;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.rgups.time.R;
import ru.rgups.time.utils.ConstUtils;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter{
	
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
	private int mBlueColor;
	private int mGreenColor;

	private View leftIndicator;

	private View rightIndicator;

	private View topIndicator;

	private View bootomIndicator;

	private int mLastSelected = -1;
	
	public CalendarAdapter(Context context){
		mGreenColor = context.getResources().getColor(R.color.theme_green);
		mBlueColor = context.getResources().getColor(R.color.theme_blue);
		mCalendar = new GregorianCalendar();
		mCalendar.setTime(Calendar.getInstance().getTime());
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return DAY_COUNT;
	}
	
	public void setSelectedItem(int selecteNumber){
		this.mSelectedItem = selecteNumber;
	}
	
	@Override
	public Object getItem(int position) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, position);
		return calendar;
	}
	

	@Override
	public long getItemId(int position) {
		return position+DAY_OFFSET;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
//		mView = convertView;
//		if(mView == null){
			mView = mInflater.inflate(R.layout.calendar_element, null);
			 mView.setTag(999);
/*			mHolder = new ViewHolder(mView);
			mView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) mView.getTag();
		}*/
			 if(mLastSelected == position){
				 mView.setSelected(true);
			 }else{
				 mView.setSelected(false);
			 }
		  TextView text = (TextView) mView.findViewById(R.id.calendar_element_text);
		  TextView dayOfWeek = (TextView) mView.findViewById(R.id.calendar_element_day_of_week);
		  leftIndicator = mView.findViewById(R.id.calendar_left_indicator);
		  rightIndicator = mView.findViewById(R.id.calendar_right_indicator);
		  topIndicator = mView.findViewById(R.id.calendar_top_indicator);
		  bootomIndicator = mView.findViewById(R.id.calendar_bottom_indicator);

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
//		  mView.setTag(999);
		
		//mHolder.calendar.set(Calendar.DAY_OF_YEAR, position+DAY_OFFSET);
		//mHolder.text.setText(Integer.toString(mHolder.calendar.get(Calendar.DAY_OF_MONTH)));
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
			Calendar calendar = Calendar.getInstance();
			int dayOffset = calendar.getMaximum(Calendar.DAY_OF_YEAR)-DAY_COUNT;
			return dayOffset;
		}else{
			return 1;  //because first position = 0
		}
	
	}
	
	private static boolean getPointOfReference(){
		Calendar calendar = Calendar.getInstance();
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
	
	private boolean isOverLine(Date date){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);	
		boolean currentWeekIsParity = (weekOfYear%2)==0;
	//	Log.e("huy",""+currentWeekIsParity);
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
