package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.model.DataManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeachersCalendarAdapter extends BaseCalendarAdapter{
	private String mTeachersName;
	private ColorStateList mLessonFreeBg;
	
	public TeachersCalendarAdapter(Context context, String teacherName) {
		super(context);
		mTeachersName = teacherName;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v =  super.getView(position, convertView, parent);
		if(mLessonMatrix[LessonManager.getInstance().getDayOfWeek(position)-1] [ LessonManager.getInstance().getWeekState(position+LessonManager.DAY_OFFSET)]){
			
			v.setBackgroundResource(R.drawable.calendar_list_selector);
			mHolder.getDayOfWeek().setTextColor(mBlueColor);
		} else {
			v.setBackgroundResource(R.drawable.lesson_free_calendar_list_selector);
			mHolder.getDayOfWeek().setTextColor(mLessonFreeColor);
			
		}
		return v;
	}
	
	
	
	public void setTeachersName(String mTeachersName) {
		this.mTeachersName = mTeachersName;
	}

	@Override
	protected boolean[][] getLessonMatrix() {
		return LessonManager.getInstance().getTeacherLessonMatrix(mTeachersName);
	}

}
