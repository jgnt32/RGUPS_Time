package ru.rgups.time.receiver;

import ru.rgups.time.R;
import ru.rgups.time.activities.HomeWorkActivity;
import ru.rgups.time.fragments.HomeWorkEditFragment;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.utils.PreferenceManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class HomeWorkNotificationReceiver extends BroadcastReceiver{

	private NotificationCompat.Builder mBuilder ;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		HomeWork hw = DataManager.getInstance().getHomeWork(intent.getExtras().getLong(HomeWorkEditFragment.HOMEWORK_ID));

		if(hw != null){
			if(hw.getGroupId() == PreferenceManager.getInstance().getGroupId()){
				if(!hw.isComplite()){
					LessonListElement lesson = DataManager.getInstance().getLesson(hw.getLessonId());
					
					Intent i = new Intent(context, HomeWorkActivity.class);
					i.putExtra(HomeWorkActivity.LAUNCH_TYPE, HomeWorkActivity.EDIT);
					i.putExtra(HomeWorkEditFragment.HOMEWORK_ID, hw.getId());
					
				    PendingIntent pIntent = PendingIntent.getActivity(context, (int) hw.getId(), i, 0);
					
					mBuilder = new NotificationCompat.Builder(context)
						    .setSmallIcon(R.drawable.ic_launcher)
						    .setContentTitle(lesson.getInformation().get(0).getTitle())
						    .setContentIntent(pIntent)
						    
						    .setContentText(hw.getMessage());
							
					Notification n = mBuilder.build();
					
					NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					nm.notify((int) hw.getId(), n);
				}
			
			}
		}
		
		
	
	}

}
