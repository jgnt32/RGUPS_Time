package ru.rgups.time.spice;

import org.springframework.web.client.RestClientException;

import roboguice.util.temp.Ln;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.utils.PreferenceManager;
import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class TimeTableRequest extends SpringAndroidSpiceRequest< LessonList > {

    private String baseUrl;

    
    
    public TimeTableRequest(String id) {
        super( LessonList.class );
        this.baseUrl =  "http://rgups.ru/time/xml/?group="+id;
        Log.e("TimeTableRequest","url:"+baseUrl);
    }

    @Override
    public LessonList loadDataFromNetwork() throws RestClientException {
    	LessonList list = getRestTemplate().getForObject( baseUrl, LessonList.class );
    	DataManager.getInstance().saveLessons(list, PreferenceManager.getInstance().getGroupId());
    	return list;
    }

}
