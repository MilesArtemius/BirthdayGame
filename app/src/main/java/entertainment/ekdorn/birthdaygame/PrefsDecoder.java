package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsDecoder {
    public static final String PREFERENCES_NAME = "birthday_game_shared_local";

    public static final String CURRENT_PRESENT = "current_present";
    public static final String CURRENT_HITS = "current_hits";

    public static final String PRESENTS_GOT = "presents";

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
}
