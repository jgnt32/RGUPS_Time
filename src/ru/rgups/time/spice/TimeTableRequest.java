package ru.rgups.time.spice;

import org.springframework.web.client.RestClientException;

import roboguice.util.temp.Ln;
import ru.rgups.time.model.entity.LessonList;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class TimeTableRequest extends SpringAndroidSpiceRequest< LessonList > {

    private String baseUrl;

    
    
    public TimeTableRequest(String id) {
        super( LessonList.class );
        this.baseUrl =  "http://rgups.ru/time/xml/?group="+id;
    }

    @Override
    public LessonList loadDataFromNetwork() throws RestClientException {
        Ln.d( "Call web service " + baseUrl );
        return getRestTemplate().getForObject( baseUrl, LessonList.class );
    }

}
