package ru.rgups.time.model;

import java.sql.SQLException;

import ru.rgups.time.model.entity.Day;
import android.content.Context;

import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;

public class HelperManager {
	public static final String DB_NAME = "rgups_time_database.db";
	public static final int DB_VERSION = 	1;
	
	private static RoboSpiceDatabaseHelper mHelper;
	private static DayDAO mDayDAO;
	public static String DB_PATH;

	public static void setDbPath(Context context){
		DB_PATH = context.getFilesDir().getPath() + context.getPackageName();

	}
	
	public static RoboSpiceDatabaseHelper getHelper() {
		return mHelper;
	}
	
	public static void setHelper(RoboSpiceDatabaseHelper helper) {
		mHelper = helper;
	}
	
	public static DayDAO getDayDAO() throws SQLException{
		if(mDayDAO == null){
			mDayDAO = new DayDAO(HelperManager.getHelper().getConnectionSource(),Day.class);
		}
		return mDayDAO;		
	}
}

