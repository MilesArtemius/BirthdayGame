package entertainment.ekdorn.birthdaygame.alarmService;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import entertainment.ekdorn.birthdaygame.R;

public class MyNewIntentService extends IntentService {
    private static int NOTIFICATION_ID = 0;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = intent.getStringExtra(AlarmReceiver.MESSAGE);
        NOTIFICATION_ID++;
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("С днём рождения!")
                .setContentText(message)
                .setSmallIcon(R.drawable.ico_bnw)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.party_popper))
                .setPriority(Notification.PRIORITY_HIGH)
                .setLights(Color.RED, 1, 1)
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(message));
        Notification notificationCompat = builder.build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(NOTIFICATION_ID, notificationCompat);
    }
}
