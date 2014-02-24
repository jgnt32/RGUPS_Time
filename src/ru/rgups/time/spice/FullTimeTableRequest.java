package ru.rgups.time.spice;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.Group;
import ru.rgups.time.model.entity.GroupList;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.utils.PreferenceManager;

import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class FullTimeTableRequest extends SpringAndroidSpiceRequest< Boolean > {

	private String mGroupUrl;
	private String mGroupListUrl;
	
	public FullTimeTableRequest(Class<Boolean> clazz) {
		super(Boolean.class);
		mGroupListUrl =  "http://rgups.ru/time/xml/?faculty="+1;
		
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		GroupList groupList = getRestTemplate().getForObject( mGroupListUrl, GroupList.class );
		for(Group group : groupList.getGroupList()){
			
			LessonList list = getRestTemplate().getForObject( "http://rgups.ru/time/xml/?group="+group.getId(), LessonList.class );
			Log.e("LessonList ","title = "+list.getTitle()+"; id = "+group.getId());
	    	DataManager.getInstance().saveLessons(list, group.getId());
	    	
		}
		
		return null;
	}

}
