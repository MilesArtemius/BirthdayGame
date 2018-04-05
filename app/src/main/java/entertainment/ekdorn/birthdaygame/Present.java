package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

public class Present {
    Bitmap[] present = new Bitmap[7];
    Bitmap top;
    Bitmap side;

    int hitCount;
    int globalHitCount;
    String name;

    public Present(String name, Context context) {
        this.name = name;

        switch (this.name) {
            case PresentNames.NONE:
                this.hitCount = this.globalHitCount = PresentNames.BOOK_HITS;
                try {
                    for (int i = 0; i < 6; i++) {
                        this.present[i] = fillWithPresents(context, i);
                    }
                    this.top = getBitmapFromDrawable(context, R.drawable.top);
                    //this.side;
                } catch (NullPointerException npe) {
                    npe.fillInStackTrace();
                }
                break;

            case PresentNames.BOOK:
                this.hitCount = this.globalHitCount = PresentNames.BOOK_HITS;
                try {
                    for (int i = 0; i < 6; i++) {
                        this.present[i] = fillWithPresents(context, i);
                    }
                    this.top = getBitmapFromDrawable(context, R.drawable.top);
                    //this.side;
                } catch (NullPointerException npe) {
                    npe.fillInStackTrace();
                }
                break;

            case PresentNames.SPECIAL:
                this.hitCount = this.globalHitCount = PresentNames.SPECIAL_HITS;
                try {
                    for (int i = 0; i < 6; i++) {
                        this.present[i] = fillWithPresents(context, i);
                    }
                    this.top = getBitmapFromDrawable(context, R.drawable.top);
                    //this.side;
                } catch (NullPointerException npe) {
                    npe.fillInStackTrace();
                }
                break;
        }
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }




    public static Bitmap fillWithPresents(Context context, int drawableId) {
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
