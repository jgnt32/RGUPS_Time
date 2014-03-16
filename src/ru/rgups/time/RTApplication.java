package ru.rgups.time;

import java.sql.SQLException;

import ru.rgups.time.model.HelperManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.LessonTableModel;
import ru.rgups.time.model.entity.Facultet;
import ru.rgups.time.model.entity.Group;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.utils.DialogManager;
import ru.rgups.time.utils.NotificationManager;
import ru.rgups.time.utils.PreferenceManager;
import android.app.Application;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;

public class RTApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		PreferenceManager.initalize(getApplicationContext());
		DialogManager.initInstatnce(getApplicationContext());
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
