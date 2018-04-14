package entertainment.ekdorn.birthdaygame;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

import entertainment.ekdorn.birthdaygame.alarmService.AlarmReceiver;

public class PrefsDecoder {
    private static final String PREFERENCES_NAME = "birthday_game_shared_local";

    private static final String CURRENT_PRESENT = "current_present";
    private static final String CURRENT_HITS = "current_hits";

    private static final String PRESENTS_GOT = "presents";

    private static final String SOUND_ON = "sound";

    private static final String ALARM_SET = "alarm";

    public static void saveGame(Context context, Present current) {
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(CURRENT_PRESENT, current.name).putInt(CURRENT_HITS, current.hitCount).apply();
    }

    public static Present loadGame(Context context) {
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        Present current = new Present(prefs.getString(CURRENT_PRESENT, AssetConstants.NONE), context);
        current.setHitCount(prefs.getInt(CURRENT_HITS, AssetConstants.NONE_HITS));

        return current;
    }



    public static void obtainPresent(Context context) {
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        int got = prefs.getInt(PRESENTS_GOT, 0);
        prefs.edit().putInt(PRESENTS_GOT, got + 1).apply();
    }

    public static int howManyPresents(Context context) {
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(PRESENTS_GOT, 0);
    }



    public static boolean isSoundOn(Context context) {
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        return prefs.getBoolean(SOUND_ON, false);
    }

    public static void turnSound(Context context, boolean turn) {
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        prefs.edit().putBoolean(SOUND_ON, turn).apply();
    }



    public static void setAlarm(Context context, boolean force) {
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        if ((!prefs.getBoolean(ALARM_SET, false)) || (force)) {
            Log.e("TAG", "setAlarm: ALARMS SET" );
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
            alarmIntent.putExtra(AlarmReceiver.MESSAGE, AlarmReceiver.NOT_NOTIFICATION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            //CUSTOM INTENTS WITH MESSAGES:

            setSingleAlarm(1000, 9, 35, "Привет, прекрасная Маша!!\nС днём рождения! \uD83C\uDF89\uD83C\uDF89\uD83C\uDF8A\nЖелаю тебе отличного дня!", context);
            setSingleAlarm(1500, 14, 43, "Пусть у тебя в жизни всё сбудется! Всё, что ты захочешь, конечно. И то, что будет тебе полезно...\nВ общем, всего самого лучшего тебе!", context);
            setSingleAlarm(1500, 21, 6, "Будь жива и здорова.\nИ улыбнись)\nНу а мне пора.", context);
            setSingleAlarm(2000, 22, 27, "Я рад и горжусь тем, что мы дружим\uD83D\uDE09", context);
            setSingleAlarm(2500, 22, 34, "Спокойной ночи!)", context);

            prefs.edit().putBoolean(ALARM_SET, true).apply();
        }
    }

    private static void setSingleAlarm(int DEBUG, int hours, int minutes, String message, Context context) {
        Log.e("TAG", "setSingleAlarm: "  + message);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeInMillis(System.currentTimeMillis() + DEBUG); //TODO: replace;
        //calendar.set(Calendar.MONTH, Calendar.APRIL);
        //calendar.set(Calendar.DAY_OF_MONTH, 29);
        //calendar.set(Calendar.HOUR_OF_DAY, hours);
        //calendar.set(Calendar.MINUTE, minutes);
        //calendar.set(Calendar.SECOND, 1);
        alarmIntent.putExtra(AlarmReceiver.MESSAGE, message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, minutes, alarmIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
