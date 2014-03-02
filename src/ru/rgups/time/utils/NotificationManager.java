package ru.rgups.time.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.rgups.time.fragments.HomeWorkEditFragment;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.receiver.HomeWorkNotificationReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationManager {
	
	private static NotificationManager mInstance;
	private Context mContext;
	private AlarmManager mAlarmManager;

	private GregorianCalendar calendar = new GregorianCalendar();
	
	public static void initInstance(Context context){
		mInstance = new NotificationManager(context);
	}

	public static NotificationManager getInstance(){
		return mInstance;
	}

	
	private NotificationManager(Context context){
		mContext = context;
		calendar.setTime(Calendar.getInstance().getTime());

		
		 mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		 
		 for(long hwId : DataManager.getInstance().getTopicalHomeWorksIds()){
			 Intent intent = new Intent(mContext, HomeWorkNotificationReceiver.class);
			 intent.putExtra(HomeWorkEditFragment.HOMEWORK_ID, hwId);
			 PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, (int) hwId,
			 intent, PendingIntent.FLAG_CANCEL_CURRENT );			 
			 calendar.add(GregorianCalendar.SECOND, 30);
			 mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
		 }
	}
	
	
	public void addNewNotification(HomeWork hw){
		 Intent intent = new Intent(mContext, HomeWorkNotificationReceiver.class);
		 intent.putExtra(HomeWorkEditFragment.HOMEWORK_ID, hw.getId());
		 PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, (int) hw.getId(),
		 intent, PendingIntent.FLAG_CANCEL_CURRENT );			 
		 calendar.add(GregorianCalendar.SECOND, 30);
		 mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	}
	
	public void removeNotification(HomeWork hw){
		Intent intent = new Intent(mContext, HomeWorkNotificationReceiver.class);
		 intent.putExtra(HomeWorkEditFragment.HOMEWORK_ID, hw.getId());
		 PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, (int) hw.getId(),
		 intent, PendingIntent.FLAG_CANCEL_CURRENT );			 
		 calendar.add(GregorianCalendar.SECOND, 30);
		 mAlarmManager.cancel(pendingIntent);
	}
	
}
