package ru.rgups.time.services;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import ru.rgups.time.MainActivity;
import ru.rgups.time.R;
import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.utils.PreferenceManager;

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
        if(PreferenceManager.getInstance().statusBarNotificationIsEnabled()) {
            createNotification();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotification() {
        mLesson = LessonManager.getInstance().getCurrentLesson();
        mClosestLesson = LessonManager.getInstance().getClosestLesson();

        if(mClosestLesson != null){
            Log.e("LessonNotificationService", "onBind");
            Notification n = new NotificationCompat.Builder(this)
           /* setContentText(mClosestLesson.getInformation().get(0).getTitle())
            .setContentTitle("Ближайшая пара")*/
                    .setContentIntent(getPendingIntent(mClosestLesson, mLesson))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContent(getRemoteViews())
                    .build();

            n.flags = Notification.FLAG_ONGOING_EVENT;
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


            notificationManager.cancelAll();
            notificationManager.notify(0, n);
        }

    }

    private RemoteViews getRemoteViews() {
        RemoteViews result;

        if(mLesson == null){
            result = new RemoteViews(getPackageName(), R.layout.notification_closest_lesson_only);
            result.setTextViewText(R.id.notification_closest_lesson_title, mClosestLesson.getTitle());
            result.setTextViewText(R.id.notification_closest_lesson_room, mClosestLesson.getRooms());
         //   result.setPendingIntentTemplate(R.id.notification_closest_lesson_cotainer, getPendingIntent(mClosestLesson));


        } else {
            result = new RemoteViews(getPackageName(), R.layout.notification_closest_and_current_lesson);

            result.setTextViewText(R.id.notification_closest_lesson_title, mClosestLesson.getTitle());
            result.setTextViewText(R.id.notification_closest_lesson_room, mClosestLesson.getRooms());
           // result.setPendingIntentTemplate(R.id.notification_closest_lesson_cotainer, getPendingIntent(mClosestLesson, mLesson));

            result.setTextViewText(R.id.notification_current_lesson_title, mLesson.getTitle());
            result.setTextViewText(R.id.notification_current_lesson_room, mLesson.getRooms());
          //  result.setPendingIntentTemplate(R.id.notification_currenr_lesson_cotainer, getPendingIntent(mLesson));

        }

        return result;
    }

    private PendingIntent getPendingIntent(LessonListElement closeLesson, LessonListElement currentLesson){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(currentLesson == null){
            notificationIntent.putExtra(MainActivity.NOTIFICATION_LESSON_ID, closeLesson.getId());
            notificationIntent.putExtra(MainActivity.NOTIFICATION_LESSON_DATE, closeLesson.getDate().getTime());

        } else {
            notificationIntent.putExtra(MainActivity.NOTIFICATION_LESSON_ID, currentLesson.getId());
            notificationIntent.putExtra(MainActivity.NOTIFICATION_LESSON_DATE, currentLesson.getDate().getTime());

        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

}
