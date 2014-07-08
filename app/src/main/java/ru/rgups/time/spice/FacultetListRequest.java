package ru.rgups.time.spice;

import org.springframework.web.client.RestClientException;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.FacultetList;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class FacultetListRequest extends SpringAndroidSpiceRequest< FacultetList > {

    private String baseUrl;

    
    
    public FacultetListRequest() {
        super( FacultetList.class );
        this.baseUrl =  "http://rgups.ru/time/xml/";
    }

    @Override
    public FacultetList loadDataFromNetwork() throws RestClientException {
    	FacultetList result = getRestTemplate().getForObject( baseUrl, FacultetList.class );
    	DataManager.getInstance().saveFacultet(result.getFacultetList());
    	return result;
    }

}
