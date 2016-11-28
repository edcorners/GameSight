package com.eddev.android.gamesight.presenter;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.model.Game;

/**
 * Created by ed on 11/27/16.
 */

public class GameReleaseNotification extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "game_release_notification_id";
    public static String NOTIFICATION = "game_release_notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);
    }

    public static void scheduleNotification(final Context context, final Game game) {
        int notificationId = game.getId();
        //delay is after how much time(in millis) from current time you want to schedule the notification
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.notification_title, context.getString(R.string.app_title));
        String description = String.format(context.getString(R.string.game_released_notification),game.getName());
        remoteViews.setTextViewText(R.id.notification_description, description);
        remoteViews.setImageViewResource(R.id.notification_game_thumb, R.mipmap.ic_launcher);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, GameDetailActivity.class);
        intent.putExtra(context.getString(R.string.parcelable_game_key), game);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();
        // https://github.com/square/picasso/issues/1034
        // Picasso.with(context).load(game.getThumbnailUrl()).into(remoteViews, R.id.notification_game_thumb, notificationId, notification);
        /* Same behavior as Picasso

        NotificationTarget notificationTarget;
        notificationTarget = new NotificationTarget(
                context,
                remoteViews,
                R.id.notification_game_thumb,
                notification,
                notificationId);

        Glide.with( context.getApplicationContext() )
                .load( game.getThumbnailUrl() )
                .asBitmap()
                .into( notificationTarget );*/

        Intent notificationIntent = new Intent(context, GameReleaseNotification.class);
        notificationIntent.putExtra(GameReleaseNotification.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(GameReleaseNotification.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, game.getExpectedReleaseDate().getTime() , pendingIntent);
    }
}
