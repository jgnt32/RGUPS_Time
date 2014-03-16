package ru.rgups.time.spice;

import org.springframework.web.client.RestClientException;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.GroupList;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class GroupListRequest extends SpringAndroidSpiceRequest< GroupList > {

    private String baseUrl;

    private long mFacultetId;
    
    public GroupListRequest(Long facultetId) {
        super( GroupList.class );
        this.baseUrl =  "http://rgups.ru/time/xml/?faculty="+Long.toString(facultetId);
        mFacultetId = facultetId;
    }

    @Override
    public GroupList loadDataFromNetwork() throws RestClientException {
        
        GroupList result = getRestTemplate().getForObject( baseUrl, GroupList.class );
        DataManager.getInstance().saveGroup(result.getGroupList(), mFacultetId);
        return result;
    }

}
