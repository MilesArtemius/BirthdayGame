package entertainment.ekdorn.birthdaygame.assetsWorking;

public class AssetConstants {
    public static final String COVER = "cover%s.png";
    public static final int COVER_count = 5;
    public static final String BACKGROUND = "background.png";

    public static final String MUSIC_ON = "music_on.png";
    public static final String MUSIC_OFF = "music_mute.png";
    public static final String MUSIC = "music%s.mp3";
    public static final int MUSIC_count = 6;

    public static final String BOOK = "book";
    private static final int BOOK_HITS = 20;
    private static final int BOOK_HITS_LONG = 200;
    public static final String BOOK_BODY = "book_body.png";
    public static final String BOOK_TOP = "book_top.png";
    public static final String BOOK_SIDE = "book_side.png";
    public static final String BOOK_PREVIEW = "book_preview.png";

    public static final String SPECIAL = "special";
    private static final int SPECIAL_HITS = 200;
    private static final int SPECIAL_HITS_LONG = 2000;
    public static final String SPECIAL_BODY = "special_body.png";
    public static final String SPECIAL_TOP = "special_top.png";
    public static final String SPECIAL_SIDE = "special_side.png";
    public static final String SPECIAL_PREVIEW = "special_preview.png";

    public static final String CARDBOARD = "cardboard";
    private static final int CARDBOARD_HITS = 100;
    private static final int CARDBOARD_HITS_LONG = 1000;
    public static final String CARDBOARD_BODY = "cardboard_body.png";
    public static final String CARDBOARD_TOP = "cardboard_top.png";
    public static final String CARDBOARD_SIDE = "cardboard_side.png";
    public static final String CARDBOARD_PREVIEW = "cardboard_preview.png";


    public static final String SMTH_ELSE = "else";
    private static final int SMTH_ELSE_HITS = 10;
    private static final int SMTH_ELSE_HITS_LONG = 100;
    public static final String SMTH_ELSE_BODY = "something_else_body.png";
    public static final String SMTH_ELSE_TOP = "something_else_top.png";
    public static final String SMTH_ELSE_SIDE = "else_side.png";
    public static final String SMTH_ELSE_CONTENT = "something_content%s.png";
    public static final int SMTH_ELSE_CONTENT_count = 13;

    public static final String NONE = "none";
    public static final int NONE_HITS = 10000;
    public static final String NONE_BODY = "none_body.png";

    public static int getConstForPresent(String presentName, boolean longGame) {
        switch (presentName) {
            case BOOK:
                return (longGame ? BOOK_HITS_LONG : BOOK_HITS);
            case SPECIAL:
                return (longGame ? SPECIAL_HITS_LONG : SPECIAL_HITS);
            case CARDBOARD:
                return (longGame ? CARDBOARD_HITS_LONG : CARDBOARD_HITS);
            case SMTH_ELSE:
                return (longGame ? SMTH_ELSE_HITS_LONG : SMTH_ELSE_HITS);
            default:
                return NONE_HITS;
        }
    }
}
