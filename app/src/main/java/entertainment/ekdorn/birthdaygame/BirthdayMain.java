package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BirthdayMain extends AppCompatActivity {

    Bitmap [] present = new Bitmap[7];
    Bitmap top;

    static int hitCount;

    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GraphicsView main = new GraphicsView(this);

        try {
            for (int i = 0; i < 6; i++) {
                present[i] = fillWithPresents(this, i);
            }
            top = getBitmapFromDrawable(this, R.drawable.top);
        } catch (NullPointerException npe) {
            npe.fillInStackTrace();
        }
        main.setPresent(present);
        main.setTop(top);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains("hit_count")) {
            hitCount = prefs.getInt("hit_count", 0);
        } else {
            prefs.edit().putInt("hit_count", 10).apply();
            hitCount = 10;
        }
        hitCount = 10; //TODO: delete;
        main.setHitCount(hitCount);

        main.setGlobalCount(10);

        setContentView(main);
    }

    @Override
    protected void onPause() {
        prefs.edit().putInt("hit_count", hitCount).apply();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        for (Bitmap bmp: present) {
            bmp.recycle();
        }
        super.onDestroy();
    }

    public static Bitmap fillWithPresents(Context context,  int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.present0);;

        switch (drawableId) {
            case 0:
                drawable = ContextCompat.getDrawable(context, R.drawable.present0);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(context, R.drawable.present1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(context, R.drawable.present2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(context, R.drawable.present3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(context, R.drawable.present4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(context, R.drawable.present5);
                break;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            try {
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                return bitmap;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            try {
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                return bitmap;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
