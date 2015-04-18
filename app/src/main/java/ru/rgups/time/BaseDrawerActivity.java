package ru.rgups.time;

import android.support.v7.app.ActionBarActivity;

import com.octo.android.robospice.SpiceManager;

import ru.rgups.time.spice.SampleSpiceService;

public class BaseDrawerActivity extends ActionBarActivity{

	private SpiceManager mSpiceManager = new SpiceManager(SampleSpiceService.class);
	
	@Override
	protected void onStart() {
		super.onStart();
		if(!mSpiceManager.isStarted()){
			mSpiceManager.start(this);
		}
		
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mSpiceManager.isStarted()){
			mSpiceManager.shouldStop();
		}
	}
	
	public SpiceManager getSpiceManager() {
		return mSpiceManager;
	}
}
