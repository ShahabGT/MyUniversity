package projects.shahabgt.com.myuniversity.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import projects.shahabgt.com.myuniversity.R;
import projects.shahabgt.com.myuniversity.classes.DatabaseOperations;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        String body = remoteMessage.getNotification().getBody();
//        String title = remoteMessage.getNotification().getTitle();
       String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(remoteMessage.getSentTime()));
//        String click_action = remoteMessage.getNotification().getClickAction();
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");
        String sender = data.get("sender");
        String click_action = data.get("click_action");
        DatabaseOperations db = new DatabaseOperations(this);
        db.insert(this,title,body,sender,date);
        db.close();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(click_action);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setPriority(0);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSound(alarmSound);
        notificationBuilder.setVibrate(new long[] { 1000, 1000});
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());






    }
}
