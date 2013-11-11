package ru.rgups.time;

import ru.rgups.time.model.HelperManager;
import android.app.Application;
import android.util.Log;

import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;

public class RTApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		
		if (HelperManager.getHelper() == null) {
			RoboSpiceDatabaseHelper databaseHelper = new RoboSpiceDatabaseHelper(this, HelperManager.DB_NAME, HelperManager.DB_VERSION);
			HelperManager.setHelper(databaseHelper);
			Log.e("MyApplication", "MyApplication");
		}
	
	}
	
	

}
