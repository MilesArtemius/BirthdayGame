package entertainment.ekdorn.birthdaygame.assetsWorking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import entertainment.ekdorn.birthdaygame.GraphicsView;

public class AssetStore {
    private static AssetStore ita;

    private HashMap<String, Bitmap> all;

    public static Bitmap getBitmap(String key, Context context) {
        if (ita.all.containsKey(key)) {
            return ita.all.get(key);
        } else if ((key.contains("content")) || (key.contains("preview"))) {
            return Bitmap.createScaledBitmap(getAsset(context, key), GraphicsView.bestFitW, GraphicsView.bestFitH, true);
        } else {
            return getAsset(context, key);
        }
    }

    private static Bitmap getAsset(Context context, String assetName) {
        try {
            InputStream ims = context.getAssets().open(assetName);
            Drawable drawable = Drawable.createFromStream(ims, null);

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
        catch(IOException ex) {
            return null;
        }
    }

    public static void loadAll(Context context) {
        ita = new AssetStore();
        ita.all = new HashMap<>();

        Thread loader = new Thread() {
            @Override
            public void run() {
                ita.all.put(AssetConstants.BACKGROUND, getAsset(context, AssetConstants.BACKGROUND));
                ita.all.put(AssetConstants.MUSIC_ON, getAsset(context, AssetConstants.MUSIC_ON));
                ita.all.put(AssetConstants.MUSIC_OFF, getAsset(context, AssetConstants.MUSIC_OFF));
                for (int i = 1; i < 6; i++) {
                    String key = String.format(AssetConstants.COVER, i);
                    ita.all.put(key, getAsset(context, key));
                }

                //ita.all.put(AssetConstants.BOOK, getAsset(context, AssetConstants.BOOK));
                ita.all.put(AssetConstants.BOOK_BODY, getAsset(context, AssetConstants.BOOK_BODY));
                ita.all.put(AssetConstants.BOOK_TOP, getAsset(context, AssetConstants.BOOK_TOP));
                //ita.all.put(AssetConstants.BOOK_SIDE, getAsset(context, AssetConstants.BOOK_SIDE));
                //ita.all.put(AssetConstants.BOOK_PREVIEW, getAsset(context, AssetConstants.BOOK_PREVIEW));
                ita.all.put(AssetConstants.BOOK_PREVIEW, Bitmap.createScaledBitmap(getAsset(context, AssetConstants.BOOK_PREVIEW), GraphicsView.bestFitW, GraphicsView.bestFitH, true));

                //ita.all.put(AssetConstants.SPECIAL, getAsset(context, AssetConstants.SPECIAL));
                ita.all.put(AssetConstants.SPECIAL_BODY, getAsset(context, AssetConstants.SPECIAL_BODY));
                ita.all.put(AssetConstants.SPECIAL_TOP, getAsset(context, AssetConstants.SPECIAL_TOP));
                //ita.all.put(AssetConstants.SPECIAL_SIDE, getAsset(context, AssetConstants.SPECIAL_SIDE));
                //ita.all.put(AssetConstants.SPECIAL_PREVIEW, getAsset(context, AssetConstants.SPECIAL_PREVIEW));
                ita.all.put(AssetConstants.SPECIAL_PREVIEW, Bitmap.createScaledBitmap(getAsset(context, AssetConstants.SPECIAL_PREVIEW), GraphicsView.bestFitW, GraphicsView.bestFitH, true));

                //ita.all.put(AssetConstants.CARDBOARD, getAsset(context, AssetConstants.CARDBOARD));
                ita.all.put(AssetConstants.CARDBOARD_BODY, getAsset(context, AssetConstants.CARDBOARD_BODY));
                ita.all.put(AssetConstants.CARDBOARD_TOP, getAsset(context, AssetConstants.CARDBOARD_TOP));
                //ita.all.put(AssetConstants.CARDBOARD_SIDE, getAsset(context, AssetConstants.CARDBOARD_SIDE));
                //ita.all.put(AssetConstants.CARDBOARD_PREVIEW, getAsset(context, AssetConstants.CARDBOARD_PREVIEW));
                ita.all.put(AssetConstants.CARDBOARD_PREVIEW, Bitmap.createScaledBitmap(getAsset(context, AssetConstants.CARDBOARD_PREVIEW), GraphicsView.bestFitW, GraphicsView.bestFitH, true));

                //ita.all.put(AssetConstants.SMTH_ELSE, getAsset(context, AssetConstants.SMTH_ELSE));
                ita.all.put(AssetConstants.SMTH_ELSE_BODY, getAsset(context, AssetConstants.SMTH_ELSE_BODY));
                ita.all.put(AssetConstants.SMTH_ELSE_TOP, getAsset(context, AssetConstants.SMTH_ELSE_TOP));
                //ita.all.put(AssetConstants.SMTH_ELSE_SIDE, getAsset(context, AssetConstants.SMTH_ELSE_SIDE));

                //ita.all.put(AssetConstants.NONE, getAsset(context, AssetConstants.NONE));
                //ita.all.put(AssetConstants.BACKGROUND, getAsset(context, AssetConstants.BACKGROUND));

                for (int i = 1; i < 2; i++) {
                    String key = String.format(AssetConstants.SMTH_ELSE_CONTENT, i);
                    //ita.all.put(key, getAsset(context, key));
                    ita.all.put(key, Bitmap.createScaledBitmap( getAsset(context, key), GraphicsView.bestFitW, GraphicsView.bestFitH, true));
                }

                Log.e("TAG", "run: ALL LOADED");
            }
        };
        loader.start();
    }
}
