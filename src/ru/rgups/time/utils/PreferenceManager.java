package ru.rgups.time.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
	private final static String PLACE = "ru.rgups.time";
	private final static String FACULTET_ID = "facultet_id";
	private final static String GROUP_ID = "group_id";
	private final static String GROUP_TITLE = "group_title";
	private final static String FULL_TIME_DOWNLOADED = "full_time_downloaded";
	private final static String FACULTETS_TIME_DOWNLOADED = "facultets_time_downloaded";

	private final String DEFAULT_VALUE = "";
	
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
		return mPreference.getString(FACULTET_ID, DEFAULT_VALUE);
	}
	
	public void saveGroupId(Long id){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		mPreference.edit().putLong(GROUP_ID, id).commit();		
	}
	
	public Long getGroupId(){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		return mPreference.getLong(GROUP_ID, -1);
	}
	
	public void saveGroupTitle(String title){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		mPreference.edit().putString(GROUP_TITLE, title).commit();			
	}
	
	public String getGroupTitle(){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		return mPreference.getString(GROUP_TITLE, null);
	}
	
	public boolean isFullTimeDownloaded(){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		return mPreference.getBoolean(FULL_TIME_DOWNLOADED, false);
	}
	
	public void setFullTimeDownloaded(boolean value){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		mPreference.edit().putBoolean(FULL_TIME_DOWNLOADED, value).commit();		
	}
	
	public boolean isFacultetsTimeDowloaded(){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		return mPreference.getBoolean(FACULTETS_TIME_DOWNLOADED, false);
	}
	
	public void setFucultetsTimeDownloaded(boolean value){
		mPreference = mContext.getSharedPreferences(PLACE, Context.MODE_PRIVATE);
		mPreference.edit().putBoolean(FACULTETS_TIME_DOWNLOADED, value).commit();		
	}
	
	
}
