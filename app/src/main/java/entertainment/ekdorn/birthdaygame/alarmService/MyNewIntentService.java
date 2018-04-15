package entertainment.ekdorn.birthdaygame.alarmService;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;

import entertainment.ekdorn.birthdaygame.BirthdayMain;
import entertainment.ekdorn.birthdaygame.R;

public class MyNewIntentService extends IntentService {
    private static int NOTIFICATION_ID = 0;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = intent.getStringExtra(AlarmReceiver.MESSAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            NOTIFICATION_ID++;
            Notification.Builder builder = new Notification.Builder(this)
                    .setContentTitle("My Title")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ico)
                    .setLights(Color.RED, 1, 1)
                    .setVibrate(new long[]{0, 1000, 1000, 1000})
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setStyle(new Notification.BigTextStyle()
                            .bigText(message));
            Intent notifyIntent = new Intent(this, BirthdayMain.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //to be able to launch your activity from the notification
            builder.setContentIntent(pendingIntent);
            Notification notificationCompat = builder.build();
            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.notify(NOTIFICATION_ID, notificationCompat);

        } else {
            String [] lines = message.split("\n");

            for (int i = 0; i < lines.length; i++) {
                final int fin = i;
                Thread note = new Thread() {
                    @Override
                    public void run() {
                        NOTIFICATION_ID++;
                        Notification.Builder builder = new Notification.Builder(MyNewIntentService.this)
                                .setContentTitle("My Title")
                                .setContentText(lines[fin])
                                .setSmallIcon(R.drawable.ico)
                                .setLights(Color.RED, 1, 1)
                                .setVibrate(new long[]{0, 1000, 1000, 1000})
                                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                        Intent notifyIntent = new Intent(MyNewIntentService.this, BirthdayMain.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(MyNewIntentService.this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        //to be able to launch your activity from the notification
                        builder.setContentIntent(pendingIntent);
                        Notification notificationCompat = builder.getNotification();
                        NotificationManager manager = (NotificationManager) MyNewIntentService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(NOTIFICATION_ID*100 + fin, notificationCompat);
                    }
                };
                note.start();
            }
        }
    }
}
