package ru.rgups.time.adapters;

import java.util.Calendar;

import ru.rgups.time.R;
import ru.rgups.time.utils.ConstUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter{
	
	public final static int CURRENS_SEMESTR = spotSemestr();//

	public final static int DAY_COUNT = getCorrectDayCount();
	
	public final static int DAY_OFFSET = getDayOffset(); 
	
	private Calendar mCalendar;
	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	private View mView;
	public CalendarAdapter(Context context){
		
		mCalendar = Calendar.getInstance();
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return DAY_COUNT;
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
		mView = convertView;
		if(mView == null){
			mView = mInflater.inflate(R.layout.calendar_element, null);
			mHolder = new ViewHolder(mView);
			mView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) mView.getTag();
		}
		mCalendar = Calendar.getInstance();
		mCalendar.set(Calendar.DAY_OF_YEAR, position+DAY_OFFSET);
		mHolder.text.setText(Integer.toString(mCalendar.get(Calendar.DAY_OF_MONTH)));
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
	
	private class ViewHolder{
		private TextView text;
		
		
		public ViewHolder(View v) {
			text = (TextView) v.findViewById(R.id.calendar_element_text);
		}
	}
	
}
