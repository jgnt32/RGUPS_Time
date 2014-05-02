package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.model.DataManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LessonCalendarAdapter extends BaseCalendarAdapter{
	
	private int [] mHomeWorksVector = new int[LessonManager.DAY_COUNT];
	
	public LessonCalendarAdapter(Context context) {
		super(context);
		context.getResources().getColor(R.color.red);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = super.getView(position, convertView, parent);

		if(mHomeWorksVector[position] > 0){
			mHolder.getmHWIndicator().setVisibility(View.VISIBLE);
			mHolder.getHwCount().setText(Integer.toString(mHomeWorksVector[position]));
		} else {
			mHolder.getmHWIndicator().setVisibility(View.GONE);
		} 
		
		if(mLessonMatrix[getDayNumber(position)-1] [ getWeekState(position)]){
			
			v.setBackgroundResource(R.drawable.calendar_list_selector);
			mHolder.getDayOfWeek().setTextColor(mBlueColor);
		} else {
			v.setBackgroundResource(R.drawable.lesson_free_calendar_list_selector);
			mHolder.getDayOfWeek().setTextColor(mLessonFreeColor);
			
		}
		
		return v;
	}
	
	@Override
	protected void loadHomeWorkInf() {
		mHomeWorksVector = LessonManager.getInstance().getHomeWorkVector();
	}

	@Override
	protected boolean[][] getLessonMatrix() {
		return LessonManager.getInstance().getStudentLessonMatrix();
	}
	
	

}
