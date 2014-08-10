package ru.rgups.time.rest;

import android.content.Context;

import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.spice.FacultetTimeTableRequest;
import ru.rgups.time.spice.FullTimeTableRequest;
import ru.rgups.time.spice.SampleSpiceService;
import ru.rgups.time.spice.TimeTableRequest;
import ru.rgups.time.utils.PreferenceManager;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.listener.RequestListener;

public class RestManager {
	
	private static RestManager mInstance;
	private SpiceManager mSpiceManager;

    public Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    private static Context mContext;
	
	public static RestManager getInstance(){
		if(mInstance == null){
			mInstance = new RestManager();
		}
		return mInstance;
	}

    public RestManager() {
        mSpiceManager = new SpiceManager(SampleSpiceService.class);
        mSpiceManager.start(mContext);
    }

    public SpiceManager getSpiceManager() {
		return mSpiceManager;
	}


	public void timeTableRequest(RequestListener<LessonList> listener){
		String groupId = PreferenceManager.getInstance().getGroupId().toString();
		this.getSpiceManager().execute(new TimeTableRequest(groupId), listener);
	}
	
	public void fullTimeRequest(RequestListener<Boolean> listener){
		this.getSpiceManager().execute(new FullTimeTableRequest(Boolean.class), listener);
	}

	public void exucuteFacultetRequest(RequestListener<Boolean> listener){
		this.getSpiceManager().execute(new FacultetTimeTableRequest(Boolean.class), listener);
	}

    public void fetchHomeWorks(){
        this.getSpiceManager().execute(new FetchHomeWorksRequest(), null);
    }

}
