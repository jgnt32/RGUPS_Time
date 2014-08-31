package ru.rgups.time.rest;

import android.content.Context;
import android.util.Log;

import com.apigee.sdk.ApigeeClient;
import com.apigee.sdk.data.client.callbacks.ApiResponseCallback;
import com.apigee.sdk.data.client.entities.Collection;
import com.apigee.sdk.data.client.entities.Entity;
import com.apigee.sdk.data.client.response.ApiResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.utils.PreferenceManager;

/**
 * Created by timewaistinguru on 13.07.2014.
 */
public class ApigeeManager {

    public String ORGNAME = "jgnt32";
    public String APPNAME = "sandbox";

    public static final String HOME_WORK_TYPE = "home_works";
    public static final String TYPE = "type";

    private static ApigeeManager mInstance;

    private ApigeeClient mApigeeClient;

    private ApigeeManager (Context context){
//        mApigeeClient = new ApigeeClient(ORGNAME, APPNAME, context);
    }

    public static void initInstance(Context context){
        mInstance = new ApigeeManager(context);
    }

    public  static ApigeeManager getInstance() {
        return mInstance;
    }

    public void pushHomeWorkOnServer(HomeWork homeWork){

        Map data = new HashMap();
        data.put(TYPE, HOME_WORK_TYPE);
        data.put(HomeWork.MESSAGE, homeWork.getMessage());
        data.put(HomeWork.DATE, homeWork.getDate().getTime());
        data.put(HomeWork.GROUP_ID, homeWork.getGroupId());
        data.put(HomeWork.LESSON_ID, homeWork.getLessonId());

        mApigeeClient.getDataClient().createEntityAsync(data, new ApiResponseCallback() {
            @Override
            public void onResponse(ApiResponse apiResponse) {
                Log.e("ApigeeManager pushHomeWorkOnServer", "sucsess");
            }

            @Override
            public void onException(Exception e) {
                Log.e("ApigeeManager pushHomeWorkOnServer", "FAIL");

            }
        });
    }

    public void fetchHomeWorks(){
        String query = "";//"groupId = "+PreferenceManager.getInstance().getGroupId();
        mApigeeClient.getDataClient().getEntitiesAsync(HOME_WORK_TYPE, query, new ApiResponseCallback() {
            @Override
            public void onResponse(ApiResponse apiResponse) {
                for(Entity entity : apiResponse.getEntities()){
                    HomeWork homeWork = new HomeWork();
                    homeWork.setDate(new Date( entity.getLongProperty(HomeWork.DATE)));
                    homeWork.setMessage(entity.getStringProperty(HomeWork.MESSAGE));
                    homeWork.setGroupId(entity.getLongProperty(HomeWork.GROUP_ID));
                    homeWork.setLessonId(entity.getLongProperty(HomeWork.LESSON_ID));
                    DataManager.getInstance().saveHomeWork(homeWork);
                }
            }

            @Override
            public void onException(Exception e) {

            }
        });
    }

}
