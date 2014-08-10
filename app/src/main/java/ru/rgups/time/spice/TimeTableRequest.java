package ru.rgups.time.spice;

import org.springframework.web.client.RestClientException;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.utils.PreferenceManager;

import android.support.v4.util.DebugUtils;
import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class TimeTableRequest extends SpringAndroidSpiceRequest< LessonList > {

    private String baseUrl;

    
    
    public TimeTableRequest(String id) {
        super( LessonList.class );
        this.baseUrl = "http://www.jgnt32.narod.ru/xml.xml";///?group="+id;
    }

    @Override
    public LessonList loadDataFromNetwork() throws RestClientException {
        Log.e("TimeTableRequest","url:"+baseUrl);

        LessonList list = getRestTemplate().getForObject( baseUrl, LessonList.class );
        Log.e("TimeTableRequest"," reciedved lessons day "+list.getDays().size());

    	DataManager.getInstance().saveLessons(list, PreferenceManager.getInstance().getGroupId());
    	return list;
    }

}
