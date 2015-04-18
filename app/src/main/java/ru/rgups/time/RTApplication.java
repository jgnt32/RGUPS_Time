package ru.rgups.time;

import android.app.Application;
import android.content.Context;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;

import java.sql.SQLException;

import ru.rgups.time.model.HelperManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.LessonTableModel;
import ru.rgups.time.model.entity.Facultet;
import ru.rgups.time.model.entity.Group;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.rest.RestManager;
import ru.rgups.time.utils.DialogManager;
import ru.rgups.time.utils.NotificationManager;
import ru.rgups.time.utils.PreferenceManager;

public class RTApplication extends Application{

    private static Context mContext;

    public static Context getContext(){
        return mContext;
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
	
	@Override
	public void onCreate() {
		super.onCreate();

        mContext = getApplicationContext();

		HelperManager.setDbPath(getApplicationContext());
		PreferenceManager.initalize(getApplicationContext());
		DialogManager.initInstatnce(getApplicationContext());
        RestManager.setContext(getApplicationContext());
        initImageLoader(getApplicationContext());
        if (HelperManager.getHelper() == null) {
			RoboSpiceDatabaseHelper databaseHelper = new RoboSpiceDatabaseHelper(this, HelperManager.DB_NAME, HelperManager.DB_VERSION);
			HelperManager.setHelper(databaseHelper);

		}
		createTables();
		NotificationManager.initInstance(getApplicationContext());
	}

	private void createTables(){
		ConnectionSource connectionSource = HelperManager.getHelper().getConnectionSource();
		try {
			TableUtils.createTableIfNotExists(connectionSource, LessonTableModel.class);
			TableUtils.createTableIfNotExists(connectionSource, LessonInformation.class);
			TableUtils.createTableIfNotExists(connectionSource, HomeWork.class);
			TableUtils.createTableIfNotExists(connectionSource, Facultet.class);
			TableUtils.createTableIfNotExists(connectionSource, Group.class);


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
