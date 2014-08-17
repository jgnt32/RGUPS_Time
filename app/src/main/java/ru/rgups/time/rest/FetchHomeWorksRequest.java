package ru.rgups.time.rest;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import ru.rgups.time.model.HomeWorksList;
import ru.rgups.time.utils.ConstUtils;

/**
 * Created by timewaistinguru on 13.07.2014.
 */
public class FetchHomeWorksRequest extends SpringAndroidSpiceRequest<HomeWorksList> {

    public FetchHomeWorksRequest() {
        super(HomeWorksList.class);
    }

    @Override
    public HomeWorksList loadDataFromNetwork() throws Exception {

        String url = ConstUtils.HOME_WORKS_URL;
        HomeWorksList result = getRestTemplate().getForObject(url, HomeWorksList.class);
        return result;
    }


}
