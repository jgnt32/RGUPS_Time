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
import android.util.Log;

public class NotificationManager {
	
	private final int HOURS_SHIFT = 18;
	
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
		 
		 for(HomeWork hw : DataManager.getInstance().getAllHomeWorks()){
			addNewNotification(hw);
		 }
	}
	
	
	public void addNewNotification(HomeWork hw){
	/*	if(calendar.getInstance().getTimeInMillis()<hw.getDate().getTime()){
			Intent intent = new Intent(mContext, HomeWorkNotificationReceiver.class);
			intent.putExtra(HomeWorkEditFragment.HOMEWORK_ID, hw.getId());
			PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, (int) hw.getId(),
			intent, PendingIntent.FLAG_CANCEL_CURRENT );			 
			calendar.setTime(hw.getDate());
	
			calendar.add(GregorianCalendar.HOUR, -HOURS_SHIFT);
			Log.e("addNewNotification","date "+calendar.getTime().toString());
	
			mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

		}*/
	}
	
	public void removeNotification(HomeWork hw){
/*		Log.e("removeNotification",hw.getDate().toString());
		Intent intent = new Intent(mContext, HomeWorkNotificationReceiver.class);
		intent.putExtra(HomeWorkEditFragment.HOMEWORK_ID, hw.getId());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, (int) hw.getId(),
		intent, PendingIntent.FLAG_CANCEL_CURRENT );			 
		mAlarmManager.cancel(pendingIntent);*/
	}
	
}
