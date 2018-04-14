package entertainment.ekdorn.birthdaygame.alarmService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import entertainment.ekdorn.birthdaygame.PrefsDecoder;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String MESSAGE = "intent_message";
    public static final String NOT_NOTIFICATION = "year_resetter";

    @Override
    public void onReceive(Context context, Intent intent) {

        String intentMessage = intent.getStringExtra(MESSAGE);Log.e("TAG", "onReceive: GOT INTENTS " + intentMessage);
        if (intentMessage.equals(NOT_NOTIFICATION)) {
            PrefsDecoder.setAlarm(context, true);
        } else {
            Intent notificationIntent = new Intent(context, MyNewIntentService.class);
            notificationIntent.putExtra(MESSAGE, intentMessage);
            context.startService(notificationIntent);
        }
    }
}
