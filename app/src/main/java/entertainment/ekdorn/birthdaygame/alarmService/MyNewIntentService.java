package entertainment.ekdorn.birthdaygame.alarmService;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.graphics.Color;
import android.provider.Settings;

import entertainment.ekdorn.birthdaygame.BirthdayMain;
import entertainment.ekdorn.birthdaygame.R;

public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = intent.getStringExtra(AlarmReceiver.MESSAGE);

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("My Title")
                .setContentText(message)
                .setSmallIcon(R.drawable.ico)
                .setLights(Color.RED, 1,1)
                .setVibrate(new long [] {0, 1000, 1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        Intent notifyIntent = new Intent(this, BirthdayMain.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.getNotification();
        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notificationCompat);
    }
}
