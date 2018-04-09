package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class AssetStore {
    private static AssetStore ita;

    private HashMap<String, Bitmap> all;

    public static Bitmap getBitmap(String key, Context context) {
        if (ita == null) {
            ita = new AssetStore();
        }

        if (!ita.all.containsKey(key)) {
            ita.all.put(key, getAsset(context, key));
        }

        return ita.all.get(key);
    }

    public static Bitmap getBitmapExclusive(String key, Context context) {
        return getAsset(context, key);
    }

    private AssetStore() {
        this.all = new HashMap<>();
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
}
