package ru.rgups.time.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;

public class TeachersCalendarAdapter extends BaseCalendarAdapter{
	private String mTeachersName;
	private ColorStateList mLessonFreeBg;
	
	public TeachersCalendarAdapter(Context context, long teacherId) {
		super(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v =  super.getView(position, convertView, parent);
	//    mHolder.getDayOfWeek().setTextColor(mLessonFreeColor);

		return v;
	}
	
	
	
	public void setTeachersName(String mTeachersName) {
		this.mTeachersName = mTeachersName;
	}


}
