package ru.rgups.time.adapters;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.rgups.time.R;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;
import ru.rgups.time.utils.CalendarManager;
import ru.rgups.time.utils.ConstUtils;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class BaseCalendarAdapter extends BaseAdapter{
public static final String HW_DATE_FORMAT = "dd-MM-yyyy";


	public final static String DAY_OF_WEEK_FORMAT = "EE";
	private LayoutInflater mInflater;
	protected ViewHolder mHolder;
	private View mView;
	private int mSelectedItem;
	protected int mBlueColor;
	protected int mLessonFreeColor;
	protected Context mContext;
	

	private int mLastSelected = -1;

	private GregorianCalendar mTimestampCalendar;
	
	private Color mBlue;
	
	protected boolean mLessonMatrix[][] = new boolean [7][2];

	protected LoadLessonInfo mAsyncLoad;
	
	public BaseCalendarAdapter(Context context){
		mContext = context;
		mLessonFreeColor = context.getResources().getColor(R.color.lesson_free);
		mBlueColor = context.getResources().getColor(R.color.theme_blue);

		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTimestampCalendar = new GregorianCalendar();
		
		mTimestampCalendar.setTime(Calendar.getInstance().getTime());
		mTimestampCalendar.set(GregorianCalendar.HOUR, 0);
		mTimestampCalendar.set(GregorianCalendar.MINUTE, 0);
		mTimestampCalendar.set(GregorianCalendar.SECOND, 0);
		mTimestampCalendar.set(GregorianCalendar.MILLISECOND, 0);
		mAsyncLoad = new LoadLessonInfo();
		mAsyncLoad.execute();
	}
	
	@Override
	public int getCount() {
		return CalendarManager.getCorrectDayCount();
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
	

    /** you shold dived on 86000**/
	@Override
	public long getItemId(int position) {
		return CalendarManager.getDate(position);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		mView = convertView;
		if(mView == null){
			mView = mInflater.inflate(R.layout.calendar_element, null);
			mHolder = new ViewHolder(mView);
			mView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) mView.getTag();
		}
			

		  mHolder.text.setText(DateFormat.format("d",CalendarManager.getDate(position)));
		  mHolder.dayOfWeek.setText(DateFormat.format(DAY_OF_WEEK_FORMAT, CalendarManager.getDate(position)).toString().toUpperCase());
		  if(CalendarManager.isOverLine(position)){
			  mHolder.bootomIndicator.setVisibility(View.VISIBLE);
			  mHolder.topIndicator.setVisibility(View.INVISIBLE);
		  }else{
			  mHolder.bootomIndicator.setVisibility(View.INVISIBLE);
			  mHolder.topIndicator.setVisibility(View.VISIBLE);
		  }
		  
		  if(CalendarManager.isMonday(position)){
			  mHolder.leftIndicator.setVisibility(View.VISIBLE);
			  mHolder.rightIndicator.setVisibility(View.INVISIBLE);
		  }else if(CalendarManager.isSunday(position)){
				  mHolder.rightIndicator.setVisibility(View.VISIBLE);
				  mHolder.leftIndicator.setVisibility(View.INVISIBLE);
			  }else{
				  mHolder.rightIndicator.setVisibility(View.INVISIBLE);
				  mHolder.leftIndicator.setVisibility(View.INVISIBLE);
			  }

		
		return mView;
	}


	public int getDayNumber(int position){
		if(getItem(position).get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY){
			return 7;
		}else{
			return getItem(position).get(GregorianCalendar.DAY_OF_WEEK) - 1;
		}
	}
	
	protected class ViewHolder{
		private View leftIndicator;
		private View rightIndicator;
		private View topIndicator;
		private View bootomIndicator;
		private TextView dayOfWeek;
		private TextView text;
		private TextView hwCount;
		private View mHWIndicator;
		
		
		public ViewHolder(View v) {
			text = (TextView) v.findViewById(R.id.calendar_element_text);
			dayOfWeek = (TextView) v.findViewById(R.id.calendar_element_day_of_week);
			leftIndicator = v.findViewById(R.id.calendar_left_indicator);
			rightIndicator = v.findViewById(R.id.calendar_right_indicator);
			topIndicator = v.findViewById(R.id.calendar_top_indicator);
			bootomIndicator = v.findViewById(R.id.calendar_bottom_indicator);
			
			mHWIndicator = v.findViewById(R.id.calendar_element_homework_indicator);
			hwCount = (TextView) mView.findViewById(R.id.lesson_indicator_text);
		}


		public TextView getHwCount() {
			return hwCount;
		}


		public void setHwCount(TextView hwCount) {
			this.hwCount = hwCount;
		}


		public View getmHWIndicator() {
			return mHWIndicator;
		}


		public void setmHWIndicator(View mHWIndicator) {
			this.mHWIndicator = mHWIndicator;
		}


		public View getLeftIndicator() {
			return leftIndicator;
		}


		public void setLeftIndicator(View leftIndicator) {
			this.leftIndicator = leftIndicator;
		}


		public View getRightIndicator() {
			return rightIndicator;
		}


		public void setRightIndicator(View rightIndicator) {
			this.rightIndicator = rightIndicator;
		}


		public View getTopIndicator() {
			return topIndicator;
		}


		public void setTopIndicator(View topIndicator) {
			this.topIndicator = topIndicator;
		}


		public View getBootomIndicator() {
			return bootomIndicator;
		}


		public void setBootomIndicator(View bootomIndicator) {
			this.bootomIndicator = bootomIndicator;
		}


		public TextView getDayOfWeek() {
			return dayOfWeek;
		}


		public void setDayOfWeek(TextView dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}


		public TextView getText() {
			return text;
		}


		public void setText(TextView text) {
			this.text = text;
		}

		
	}
	
	public void setSelected(int last){
		mLastSelected  = last;
	}
	
	protected void loadHomeWorkInf(){
		
	}
	
	protected abstract boolean [][] getLessonMatrix();
	
	private class LoadLessonInfo extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			mLessonMatrix = getLessonMatrix();
			loadHomeWorkInf();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			BaseCalendarAdapter.this.notifyDataSetChanged();
		}
		
	}
	
}
