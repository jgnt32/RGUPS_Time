package ru.rgups.time.spice;

import org.springframework.web.client.RestClientException;

import roboguice.util.temp.Ln;
import ru.rgups.time.model.entity.FacultetList;
import ru.rgups.time.model.entity.GroupList;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class SampleXmlRequest extends SpringAndroidSpiceRequest< FacultetList > {

    private String baseUrl;

    
    
    public SampleXmlRequest() {
        super( FacultetList.class );
        this.baseUrl =  "http://rgups.ru/time/xml/";
    }

    @Override
    public FacultetList loadDataFromNetwork() throws RestClientException {
        Ln.d( "Call web service " + baseUrl );
        return getRestTemplate().getForObject( baseUrl, FacultetList.class );
    }

}
