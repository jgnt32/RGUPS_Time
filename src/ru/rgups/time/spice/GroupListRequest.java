package ru.rgups.time.spice;

import org.springframework.web.client.RestClientException;

import roboguice.util.temp.Ln;
import ru.rgups.time.model.entity.GroupList;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class GroupListRequest extends SpringAndroidSpiceRequest< GroupList > {

    private String baseUrl;

    
    
    public GroupListRequest(String id) {
        super( GroupList.class );
        this.baseUrl =  "http://rgups.ru/time/xml/?faculty="+id;
    }

    @Override
    public GroupList loadDataFromNetwork() throws RestClientException {
        Ln.d( "Call web service " + baseUrl );
        return getRestTemplate().getForObject( baseUrl, GroupList.class );
    }

}
