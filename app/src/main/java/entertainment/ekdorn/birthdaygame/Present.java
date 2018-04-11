package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Random;

public class Present {
    Bitmap[] present = new Bitmap[5];
    Bitmap body;
    Bitmap top;
    Bitmap side;

    Bitmap resized;
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
            case AssetConstants.NONE: //FIXME: очень надо, серьёзно, прям вообще, дно какое-то получилось(
                this.hitCount = this.globalHitCount = AssetConstants.NONE_HITS;
                try {
                    this.top = AssetStore.getBitmapForce(AssetConstants.BOOK_TOP, context);
                    this.body = AssetStore.getBitmapForce(AssetConstants.BOOK_BODY, context);
                    this.content = AssetStore.getBitmapForce(AssetConstants.BOOK_PREVIEW, context);
                    this.resized = Bitmap.createScaledBitmap(AssetStore.getBitmapForce("res_" + AssetConstants.BOOK_PREVIEW, context), 500, 650, true);
                    //this.side;
                } catch (NullPointerException npe) {
                    npe.fillInStackTrace();
                }
                break;

            case AssetConstants.BOOK:
                this.hitCount = this.globalHitCount = AssetConstants.BOOK_HITS;
                try {
                    this.top = AssetStore.getBitmap(AssetConstants.BOOK_TOP, context);
                    this.body = AssetStore.getBitmap(AssetConstants.BOOK_BODY, context);
                    this.content = AssetStore.getBitmap(AssetConstants.BOOK_PREVIEW, context);
                    this.resized = AssetStore.getBitmap("res_" + AssetConstants.BOOK_PREVIEW, context);
                    //this.side;
                } catch (NullPointerException npe) {
                    npe.fillInStackTrace();
                }
                break;

            case AssetConstants.SPECIAL:
                this.hitCount = this.globalHitCount = AssetConstants.SPECIAL_HITS;
                try {
                    this.top = AssetStore.getBitmap(AssetConstants.SPECIAL_TOP, context);
                    this.body = AssetStore.getBitmap(AssetConstants.SPECIAL_BODY, context);
                    this.content = AssetStore.getBitmap(AssetConstants.SPECIAL_PREVIEW, context);
                    this.resized = AssetStore.getBitmap("res_" + AssetConstants.SPECIAL_PREVIEW, context);
                    //this.side;
                } catch (NullPointerException npe) {
                    npe.fillInStackTrace();
                }
                break;

            case AssetConstants.CARDBOARD:
                this.hitCount = this.globalHitCount = AssetConstants.CARDBOARD_HITS;
                try {
                    this.top = AssetStore.getBitmap(AssetConstants.CARDBOARD_TOP, context);
                    this.body = AssetStore.getBitmap(AssetConstants.CARDBOARD_BODY, context);
                    this.content = AssetStore.getBitmap(AssetConstants.CARDBOARD_PREVIEW, context);
                    this.resized = AssetStore.getBitmap("res_" + AssetConstants.CARDBOARD_PREVIEW, context);
                    //this.side;
                } catch (NullPointerException npe) {
                    npe.fillInStackTrace();
                }
                break;

            case AssetConstants.SMTH_ELSE:
                this.hitCount = this.globalHitCount = AssetConstants.SMTH_ELSE_HITS;
                try {
                    this.top = AssetStore.getBitmap(AssetConstants.SMTH_ELSE_TOP, context);
                    this.body = AssetStore.getBitmap(AssetConstants.SMTH_ELSE_BODY, context);

                    String found = findElseContent();
                    this.content = AssetStore.getBitmap(found, context);
                    this.resized = AssetStore.getBitmap("res_" + found, context);
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

    private String findElseContent() {
        Random rnd = new Random();
        int period = rnd.nextInt(1) + 1;
        return String.format(AssetConstants.SMTH_ELSE_CONTENT, period);
    }
}
