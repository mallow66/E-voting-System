package brahim.mallow.com.evotingproject.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import brahim.mallow.com.evotingproject.Model.Election;
import brahim.mallow.com.evotingproject.R;
import brahim.mallow.com.evotingproject.ResultActivity;
import brahim.mallow.com.evotingproject.UserActivity;

/**
 * Created by brahim on 13/01/17.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage
        );
    }


    public void showNotification(RemoteMessage remoteMessage){

        Intent i = new Intent(this,ResultActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle b = new Bundle();
        Election e = new Election();
        e.setIdElection(remoteMessage.getData().get("idElection"));
        e.setNomElection(remoteMessage.getData().get("nomElection"));
        b.putSerializable("election", e);
        //i.putExtras(b);
        i.putExtra("election", e);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ResultActivity.class);

        stackBuilder.addNextIntent(i);
        PendingIntent contentIntent = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
                        | PendingIntent.FLAG_ONE_SHOT);
       // PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,Intent.FLAG_ACTIVITY_NEW_TASK);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Resultat Election")
                .setContentText(remoteMessage.getData().get("message"))
                .setSmallIcon(R.drawable.winner_icon)
                .setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }
}
