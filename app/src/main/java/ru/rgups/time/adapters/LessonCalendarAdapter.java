package ru.rgups.time.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.rgups.time.utils.CalendarManager;


public class LessonCalendarAdapter extends BaseCalendarAdapter{
	private int [] mHomeWorksVector = new int[CalendarManager.getCorrectSemestrDayCount()];
	
	public LessonCalendarAdapter(Context context) {
		super(context);

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
		return v;
	}

    public int[] getHomeWorksVector() {
        return mHomeWorksVector;
    }

    public void setHomeWorksVector(int[] mHomeWorksVector) {
        this.mHomeWorksVector = mHomeWorksVector;
    }
}
