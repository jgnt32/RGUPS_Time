package ru.rgups.time.model;

import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;

public class HelperManager {
	public static final String DB_NAME = "rgups_time_database.db";
	public static final int DB_VERSION = 	1;
	
	private static RoboSpiceDatabaseHelper mHelper;


	
	public static RoboSpiceDatabaseHelper getHelper() {
		return mHelper;
	}
	
	public static void setHelper(RoboSpiceDatabaseHelper helper) {
		mHelper = helper;
	}
}

