package ru.rgups.time.adapters;

import java.util.Calendar;

import ru.rgups.time.R;
import ru.rgups.time.utils.ConstUtils;
import android.content.Context;
import android.util.Log;
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
	
	private Calendar mCalendar;
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
	
	public CalendarAdapter(Context context){
		mGreenColor = context.getResources().getColor(R.color.theme_green);
		mBlueColor = context.getResources().getColor(R.color.theme_blue);

		mCalendar = Calendar.getInstance();
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
/*			mHolder = new ViewHolder(mView);
			mView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) mView.getTag();
		}*/
		  TextView text = (TextView) mView.findViewById(R.id.calendar_element_text); 
		  leftIndicator = mView.findViewById(R.id.calendar_left_indicator);
		  rightIndicator = mView.findViewById(R.id.calendar_right_indicator);
		  topIndicator = mView.findViewById(R.id.calendar_top_indicator);
		  bootomIndicator = mView.findViewById(R.id.calendar_bottom_indicator);
		  mCalendar = Calendar.getInstance();
		  mCalendar.set(Calendar.DAY_OF_YEAR, position+DAY_OFFSET);
		  text.setText(Integer.toString(mCalendar.get(Calendar.DAY_OF_MONTH)));
		  
	/*	  if(this.isOverLine(mCalendar)){
			  bootomIndicator.setVisibility(View.VISIBLE);
			  topIndicator.setVisibility(View.INVISIBLE);
		  }else{
			  bootomIndicator.setVisibility(View.INVISIBLE);
			  topIndicator.setVisibility(View.VISIBLE);
		  }*/
		  
		
		//mHolder.calendar.set(Calendar.DAY_OF_YEAR, position+DAY_OFFSET);
		//mHolder.text.setText(Integer.toString(mHolder.calendar.get(Calendar.DAY_OF_MONTH)));
		return mView;
	}
	
	
	private static int getCorrectDayCount(){
		if(CURRENS_SEMESTR == ConstUtils.FIRST_SEMESTR){
			return 122;						//первый семестр
		}else{
			Calendar calendar = Calendar.getInstance();
			if(calendar.getMaximum(Calendar.DAY_OF_YEAR)==355){ //второй семестр
				return 212;
			}else{
				return 213;//високосний год
			}
		}
		
	}
	
	private static int spotSemestr(){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
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
	
	private boolean isOverLine(Calendar c){
		Calendar calendar = c;
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
	
	private class ViewHolder{
		private View leftIndicator;
		private View rightIndicator;
		private View topIndicator;
		private View bootomIndicator;
		
		private TextView text;
		public ViewHolder(View v) {
			text = (TextView) v.findViewById(R.id.calendar_element_text);
			leftIndicator = v.findViewById(R.id.calendar_left_indicator);
			rightIndicator = v.findViewById(R.id.calendar_right_indicator);
			topIndicator = v.findViewById(R.id.calendar_top_indicator);
			bootomIndicator = v.findViewById(R.id.calendar_bottom_indicator);
		}
	}
	
}
