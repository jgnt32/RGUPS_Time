package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.model.DataManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LessonCalendarAdapter extends BaseCalendarAdapter{
	
	public LessonCalendarAdapter(Context context) {
		super(context);
		context.getResources().getColor(R.color.red);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = super.getView(position, convertView, parent);
		View mHWIndicator = v.findViewById(R.id.calendar_element_homework_indicator);
		TextView hwCount = (TextView) v.findViewById(R.id.lesson_indicator_text);
		
		int homeworkCount = DataManager.getInstance().getHomeWorkCountAtDay(getTimestamp(position));
		if(homeworkCount>0){
			mHWIndicator.setVisibility(View.VISIBLE);
			hwCount.setText(Integer.toString(homeworkCount));
		}else{
			mHWIndicator.setVisibility(View.GONE);
		} 
		TextView dayOfWeek = (TextView) v.findViewById(R.id.calendar_element_day_of_week);
		if(DataManager.getInstance().dayHasLesson(getDayNumber(position), getWeekState(position))){
			
			v.setBackgroundResource(R.drawable.calendar_list_selector);
			dayOfWeek.setTextColor(mBlueColor);
		}else{
			v.setBackgroundResource(R.drawable.lesson_free_calendar_list_selector);
			dayOfWeek.setTextColor(mLessonFreeColor);
			
		}
		
		return v;
	}

}
