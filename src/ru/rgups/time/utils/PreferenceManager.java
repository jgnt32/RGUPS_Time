package ru.rgups.time.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
	private final static String PLACE = "ru.rgups.time";
	private final static String FACULTET_ID = "facultet_id";
	private final static String GROUP_ID = "group_id";
	private final String DEFAULT_FALUE = "";
	
	private Context mContext;
	private SharedPreferences mPreference;
	
	private static PreferenceManager mInstanse;
	
	public PreferenceManager(Context context) {
		mContext = context;
	}

	public static synchronized PreferenceManager getInstance() {
	    return mInstanse;
	}
	
	public static synchronized PreferenceManager initalize(Context context) {
		if (mInstanse == null) {
			mInstanse = new PreferenceManager(context);
		}
	    return mInstanse;
	}
	
	public void saveFacultetId(String id){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		mPreference.edit().putString(this.FACULTET_ID, id).commit();		
	}
	
	public String getFacultetId(){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		return mPreference.getString(FACULTET_ID, DEFAULT_FALUE);
	}
	
}
