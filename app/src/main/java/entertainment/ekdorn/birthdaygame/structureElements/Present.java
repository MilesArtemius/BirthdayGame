package entertainment.ekdorn.birthdaygame.structureElements;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Random;

import entertainment.ekdorn.birthdaygame.assetsWorking.AssetConstants;
import entertainment.ekdorn.birthdaygame.assetsWorking.AssetStore;
import entertainment.ekdorn.birthdaygame.assetsWorking.PrefsDecoder;

public class Present {
    public Bitmap[] present = new Bitmap[AssetConstants.COVER_count];
    public Bitmap body;
    public Bitmap top;
    public Bitmap side;

    public String filename;
    public Bitmap content;

    public int hitCount;
    public int globalHitCount;
    public String name;

    public Present(String name, Context context) {
        this.name = name;

        for (int i = 1; i <= AssetConstants.COVER_count; i++) {
            present[i-1] = AssetStore.getBitmap(String.format(AssetConstants.COVER, i), context);
        }

        switch (this.name) {
            case AssetConstants.NONE: //FIXME: очень надо, серьёзно, прям вообще, дно какое-то получилось(
                this.hitCount = this.globalHitCount = AssetConstants.NONE_HITS;

                this.top = AssetStore.getBitmap(AssetConstants.BOOK_TOP, context);
                this.body = AssetStore.getBitmap(AssetConstants.NONE_BODY, context);
                this.content = AssetStore.getBitmap(AssetConstants.BOOK_PREVIEW, context);
                this.filename = AssetConstants.BOOK_PREVIEW;
                //this.resized ;
                //this.side;
                break;

            case AssetConstants.BOOK:
                this.hitCount = this.globalHitCount = PrefsDecoder.getHitCount(context, AssetConstants.BOOK);

                this.top = AssetStore.getBitmap(AssetConstants.BOOK_TOP, context);
                this.body = AssetStore.getBitmap(AssetConstants.BOOK_BODY, context);
                this.content = AssetStore.getBitmap(AssetConstants.BOOK_PREVIEW, context);
                this.filename = AssetConstants.BOOK_PREVIEW;
                //this.resized ;
                //this.side;
                break;

            case AssetConstants.SPECIAL:
                this.hitCount = this.globalHitCount = PrefsDecoder.getHitCount(context, AssetConstants.SPECIAL);

                this.top = AssetStore.getBitmap(AssetConstants.SPECIAL_TOP, context);
                this.body = AssetStore.getBitmap(AssetConstants.SPECIAL_BODY, context);
                this.content = AssetStore.getBitmap(AssetConstants.SPECIAL_PREVIEW, context);
                this.filename = AssetConstants.SPECIAL_PREVIEW;
                //this.resized ;
                //this.side;
                break;

            case AssetConstants.CARDBOARD:
                this.hitCount = this.globalHitCount = PrefsDecoder.getHitCount(context, AssetConstants.CARDBOARD);

                this.top = AssetStore.getBitmap(AssetConstants.CARDBOARD_TOP, context);
                this.body = AssetStore.getBitmap(AssetConstants.CARDBOARD_BODY, context);
                this.content = AssetStore.getBitmap(AssetConstants.CARDBOARD_PREVIEW, context);
                this.filename = AssetConstants.CARDBOARD_PREVIEW;
                //this.resized ;
                //this.side;
                break;

            case AssetConstants.SMTH_ELSE:
                this.hitCount = this.globalHitCount = PrefsDecoder.getHitCount(context, AssetConstants.SMTH_ELSE);

                this.top = AssetStore.getBitmap(AssetConstants.SMTH_ELSE_TOP, context);
                this.body = AssetStore.getBitmap(AssetConstants.SMTH_ELSE_BODY, context);

                String found = findElseContent();
                this.filename = found;
                this.content = AssetStore.getBitmap(found, context);
                //this.resized
                //this.side;
                break;
        }
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    private String findElseContent() {
        Random rnd = new Random();
        int period = rnd.nextInt(AssetConstants.SMTH_ELSE_CONTENT_count) + 1;
        return String.format(AssetConstants.SMTH_ELSE_CONTENT, period);
    }
}
