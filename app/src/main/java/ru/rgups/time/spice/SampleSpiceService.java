package ru.rgups.time.spice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ru.rgups.time.R;
import ru.rgups.time.model.HelperManager;
import ru.rgups.time.model.entity.Day;
import ru.rgups.time.model.entity.DoubleLine;
import ru.rgups.time.model.entity.Facultet;
import ru.rgups.time.model.entity.Lesson;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;
import android.app.Application;
import android.app.Notification;
import android.content.Intent;

import com.octo.android.robospice.SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.ormlite.InDatabaseObjectPersisterFactory;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Simple service
 * 
 * @author sni
 * 
 */
public class SampleSpiceService extends SpringAndroidSpiceService{

    private static final int WEBSERVICES_TIMEOUT = 10000;

    @Override
    public CacheManager createCacheManager( Application application ) {
        CacheManager cacheManager = new CacheManager();
        List< Class< ? >> classCollection = new ArrayList< Class< ? >>();

        // add persisted classes to class collection
        classCollection.add( Facultet.class );
        classCollection.add( LessonList.class );
        classCollection.add( Day.class );
        classCollection.add( Lesson.class );
        classCollection.add( LessonInformation.class );
        classCollection.add( DoubleLine.class );
        classCollection.add( UnderLine.class );
        classCollection.add( OverLine.class );


        if (HelperManager.getHelper() == null) {
			RoboSpiceDatabaseHelper databaseHelper = new RoboSpiceDatabaseHelper(application, HelperManager.DB_NAME, HelperManager.DB_VERSION);
			HelperManager.setHelper(databaseHelper);
		}
        InDatabaseObjectPersisterFactory inDatabaseObjectPersisterFactory = new InDatabaseObjectPersisterFactory( application, HelperManager.getHelper(), classCollection );
        cacheManager.addPersister( inDatabaseObjectPersisterFactory );
        
        return cacheManager;
    }

    @Override
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // set timeout for requests

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout( WEBSERVICES_TIMEOUT );
        httpRequestFactory.setConnectTimeout( WEBSERVICES_TIMEOUT );
        restTemplate.setRequestFactory( httpRequestFactory );

        // web services support xml responses
        SimpleXmlHttpMessageConverter simpleXmlHttpMessageConverter = new SimpleXmlHttpMessageConverter();
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        final List< HttpMessageConverter< ? >> listHttpMessageConverters = restTemplate.getMessageConverters();

        listHttpMessageConverters.add( simpleXmlHttpMessageConverter );
        listHttpMessageConverters.add( formHttpMessageConverter );
        listHttpMessageConverters.add( stringHttpMessageConverter );
        restTemplate.setMessageConverters( listHttpMessageConverters );
        return restTemplate;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

  /*  @Override
    public Notification createDefaultNotification() {
        return ;
    }*/

}
