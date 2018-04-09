package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.graphics.Bitmap;

public class Present {
    static Bitmap[] present = new Bitmap[5];
    Bitmap body;
    Bitmap top;
    Bitmap side;

    Bitmap content;

    int hitCount;
    int globalHitCount;
    String name;

    public Present(String name, Context context) {
        this.name = name;

        for (int i = 1; i < 6; i++) {
            present[i-1] = AssetStore.getBitmap(String.format(AssetConstants.COVER, i), context);
        }

        switch (this.name) {
            case AssetConstants.NONE:
                this.hitCount = this.globalHitCount = AssetConstants.NONE_HITS;
                try {
                    this.top = AssetStore.getBitmap(AssetConstants.BOOK_TOP, context);
                    this.body = AssetStore.getBitmap(AssetConstants.BOOK_BODY, context);
                    //this.side;
                } catch (NullPointerException npe) {
                    npe.fillInStackTrace();
                }
                break;

            case AssetConstants.BOOK:
                this.hitCount = this.globalHitCount = AssetConstants.BOOK_HITS;
                try {
                    /*for (int i = 0; i < 6; i++) {
                        this.present[i] = AssetStore.getBitmap(String.format(AssetConstants.BOOK_TEXTURE, i), context);
                    }*/
                    this.top = AssetStore.getBitmap(AssetConstants.BOOK_TOP, context);
                    this.body = AssetStore.getBitmap(AssetConstants.BOOK_BODY, context);
                    //this.side;
                } catch (NullPointerException npe) {
                    npe.fillInStackTrace();
                }
                break;

            case AssetConstants.SPECIAL:
                this.hitCount = this.globalHitCount = AssetConstants.SPECIAL_HITS;
                try {
                    /*for (int i = 0; i < 6; i++) {
                        this.present[i] = AssetStore.getBitmap(String.format(AssetConstants.SPECIAL_TEXTURE, i), context);
                    }*/
                    this.top = AssetStore.getBitmap(AssetConstants.SPECIAL_TOP, context);
                    this.body = AssetStore.getBitmap(AssetConstants.SPECIAL_BODY, context);

                    this.content = AssetStore.getBitmapExclusive(AssetConstants.BOOK_PREVIEW, context); //FIXME: to smth_else;
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
}
