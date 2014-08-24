package ru.rgups.time.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import ru.rgups.time.R;
import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.LessonTableModel;

/**
 * Created by timewaistinguru on 23.08.2014.
 */
public class LessonNotificationService extends Service {

    private LessonListElement mLesson;
    private LessonListElement mClosestLesson;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("LessonNotificationService", "onBind");


        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLesson = LessonManager.getInstance().getCurrentLesson();

        if(mLesson == null){
            mClosestLesson = LessonManager.getInstance().getClosestLesson();
        }
        Log.e("LessonNotificationService", "onBind");
        Notification n = new Notification.Builder(this).setContentText(mClosestLesson.getInformation().get(0).getTitle())
                .setContentTitle("Ближайшая пара")
                .setSmallIcon(R.drawable.room).build();

        n.flags = Notification.FLAG_ONGOING_EVENT;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        notificationManager.notify(0, n);
        return super.onStartCommand(intent, flags, startId);
    }

}
