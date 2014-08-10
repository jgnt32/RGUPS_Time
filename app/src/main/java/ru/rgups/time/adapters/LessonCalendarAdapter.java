package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.utils.CalendarManager;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LessonCalendarAdapter extends BaseCalendarAdapter{
	private int [] mHomeWorksVector = new int[CalendarManager.getCorrectDayCount()];
	
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
