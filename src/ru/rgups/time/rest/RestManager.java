package ru.rgups.time.rest;

import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.spice.FacultetTimeTableRequest;
import ru.rgups.time.spice.FullTimeTableRequest;
import ru.rgups.time.spice.TimeTableRequest;
import ru.rgups.time.utils.PreferenceManager;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.listener.RequestListener;

public class RestManager {
	
	private static RestManager mInstance;
	private SpiceManager mSpiceManager;
	
	public static RestManager getInstance(){
		if(mInstance == null){
			mInstance = new RestManager();
		}
		
		return mInstance;
	}
	
	public SpiceManager getSpiceManager() {
		return mSpiceManager;
	}

	public void setSpiceManager(SpiceManager mSpiceManager) {
		this.mSpiceManager = mSpiceManager;
	}
	
	public void timeTableRequest(RequestListener<LessonList> listener){
		this.getSpiceManager().execute(new TimeTableRequest(PreferenceManager.getInstance().getGroupId().toString()), listener);
	}
	
	public void fullTimeRequest(RequestListener<Boolean> listener){
		this.getSpiceManager().execute(new FullTimeTableRequest(Boolean.class), listener);
	}

	public void exucuteFacultetRequest(RequestListener<Boolean> listener){
		this.getSpiceManager().execute(new FacultetTimeTableRequest(Boolean.class), listener);
	}
	
}
