package ru.rgups.time.spice;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.Facultet;
import ru.rgups.time.model.entity.FacultetList;
import ru.rgups.time.model.entity.Group;
import ru.rgups.time.model.entity.GroupList;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.utils.PreferenceManager;

public class FullTimeTableRequest extends SpringAndroidSpiceRequest< Boolean > {

	private final String mTimeUrl = "http://rgups.ru/time/xml/?group=";
	private final String mFacultetList = "http://rgups.ru/time/xml/";
	private final String mGroupListUrl = "http://jgnt32.narod.ru/xml.xml?faculty=";
	
	private String url;
	private Activity mActivity;
	
	public FullTimeTableRequest(Class<Boolean> clazz) {
		super(Boolean.class);
		 url =  mFacultetList+1;
		 
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		boolean result = false;
		try{

            fetchFacultet();
			
			PreferenceManager.getInstance().setFullTimeDownloaded(true);
			result = true;
			
			
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}

		
		return result;
	}

    private void fetchFacultet() {
        FacultetList facultetList = getRestTemplate().getForObject(mFacultetList, FacultetList.class);
        for(Facultet facultet : facultetList.getFacultetList()){
            Log.e("FullTimeTableRequest", "" + facultet.getName());
            try {
                url = TextUtils.concat(mGroupListUrl, Integer.toString(facultet.getId())).toString();
                if(!facultet.getName().trim().equalsIgnoreCase("Лицей") && !facultet.getName().equalsIgnoreCase("Техникум РГУПС")){
                    GroupList groupList = getRestTemplate().getForObject( url, GroupList.class );

                    fetchFacultetsGroupAndSave(groupList);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
          }
    }

    private void fetchFacultetsGroupAndSave(GroupList groupList) {

        for(Group group : groupList.getGroupList()){
            try {
                url = TextUtils.concat(mTimeUrl, Long.toString(group.getId())).toString();

                LessonList list = getRestTemplate().getForObject( url, LessonList.class );
                Log.e("LessonList ", "title = " + list.getTitle() + "; id = " + group.getId());
                DataManager.getInstance().saveLessons(list, group.getId());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class UpdateProgressThred extends Thread{
	
		@Override
		public void run() {
			
			
		}
	}

}
