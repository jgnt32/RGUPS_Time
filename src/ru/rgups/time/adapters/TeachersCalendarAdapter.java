package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.model.DataManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeachersCalendarAdapter extends BaseCalendarAdapter{
	private String mTeachersName;
	private ColorStateList mLessonFreeBg;
	
	public TeachersCalendarAdapter(Context context) {
		super(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent); 
		TextView dayOfWeek = (TextView) v.findViewById(R.id.calendar_element_day_of_week);

		if(DataManager.getInstance().dayHasLesson(getDayNumber(position), getWeekState(position), mTeachersName)){
			
			v.setBackgroundResource(R.drawable.calendar_list_selector);
			dayOfWeek.setTextColor(mBlueColor);
		}else{
			v.setBackgroundResource(R.drawable.lesson_free_calendar_list_selector);
			dayOfWeek.setTextColor(mRedColor);
			
		}	
		return v;
	}
	
	public void setTeachersName(String mTeachersName) {
		this.mTeachersName = mTeachersName;
	}

}
