package ru.rgups.time.spice;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.Group;
import ru.rgups.time.model.entity.GroupList;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.utils.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class FacultetTimeTableRequest extends SpringAndroidSpiceRequest< Boolean > {

	private final String mTimeUrl = "http://rgups.ru/time/xml/?group=";
	private final String mFacultetList = "http://rgups.ru/time/xml/";
	private final String mGroupListUrl = "http://rgups.ru/time/xml/?faculty=";
	
	private String url;
	
	public FacultetTimeTableRequest(Class<Boolean> clazz) {
		super(Boolean.class);
		 url =  mFacultetList+1;
		
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		
		url = TextUtils.concat(mGroupListUrl,Long.toString(PreferenceManager.getInstance().getFacultetId())).toString();
		GroupList groupList = getRestTemplate().getForObject( url, GroupList.class );
	
		for(Group group : groupList.getGroupList()){
			url = TextUtils.concat(mTimeUrl, Long.toString(group.getId())).toString();
			
			LessonList list = getRestTemplate().getForObject( url, LessonList.class );
			Log.e("LessonList ","title = "+list.getTitle()+"; id = "+group.getId());
	    	DataManager.getInstance().saveLessons(list, group.getId());
	    	
		}
		return null;
	}
}
