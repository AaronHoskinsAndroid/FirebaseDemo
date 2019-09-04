package examples.aaronhoskins.com.firebasedemo;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage;

public class FirebasePushService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //Get info from the received message
        final RemoteMessage.Notification receivedNotification = remoteMessage.getNotification();
        final String titleOfNotifacation = receivedNotification.getTitle();
        final String messageInNotifaction = receivedNotification.getBody();
        //Setup what will open or occur when the notification is clicked
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        //If you want a sound with your notification, set up
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Build the notification
        NotificationCompat.Builder notificationToPost
                = new NotificationCompat.Builder(this, "channelId");
        notificationToPost.setContentTitle(titleOfNotifacation);
        notificationToPost.setContentText(messageInNotifaction);
        notificationToPost.setContentIntent(pendingIntent);
        notificationToPost.setSound(soundUri);
        notificationToPost.setSmallIcon(R.drawable.common_full_open_on_phone);

        //Get the notification Manager
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //Post the notification
        notificationManager.notify(0, notificationToPost.build());



    }
}
