package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.Random;

import entertainment.ekdorn.birthdaygame.assetsWorking.AssetConstants;
import entertainment.ekdorn.birthdaygame.assetsWorking.AssetStore;
import entertainment.ekdorn.birthdaygame.assetsWorking.PrefsDecoder;
import entertainment.ekdorn.birthdaygame.structureElements.MusicPlayer;
import entertainment.ekdorn.birthdaygame.structureElements.Present;

/**
 * Created by User on 01.04.2018.
 */

public class GraphicsView extends View {

    public static final int bestFitW = 500, bestFitH = 650;

    private Present current;

    private int pointMeasure;

    private boolean preset;
    private boolean won;

    private float rad, finrad, normrad;
    private Paint paint, textPaint, liningPaint, animationPaint, backgroungPaint;
    private float degrees; // top rotation degree

    private Matrix matrix;
    private Listener listener;

    private float animationCounter = 20;
    private boolean animationGoes, colorGoes;
    private int animation = 0;

    private String presentNumber;

    private Bitmap soundOnButton, soundOffButton;
    private boolean soundOn;

    //SETTERS:

    public void setListener (Listener listener) {
        this.listener = listener;
    }

    public void setPresent(Present actual) {
        current = actual;
        if (current.hitCount == 0) {
            won = true;
            setHandler();
        } else {
            won = false;
            preset = false;
        }
        switch (actual.name) {
            case AssetConstants.BOOK:
                presentNumber = "первый";
                break;
            case AssetConstants.SPECIAL:
                presentNumber = "второй";
                break;
            case AssetConstants.CARDBOARD:
                presentNumber = "третий";
                break;
            default:
                presentNumber = "новый";
                break;
        }
    }

    //GETTERS:

    public Present getCurrent() {
        return current;
    }

    //CONSTRUCTOR:

    public GraphicsView(Context context) {
        super(context);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint();
        liningPaint = new Paint();
        animationPaint = new Paint();
        backgroungPaint = new Paint();

        backgroungPaint.setAlpha(100);

        paint.setAntiAlias(true);

        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.RED);
        textPaint.setTextAlign(Paint.Align.CENTER);

        liningPaint.setStyle(Paint.Style.STROKE);
        liningPaint.setStrokeWidth(1);
        liningPaint.setColor(Color.WHITE);
        liningPaint.setTextAlign(Paint.Align.CENTER);

        matrix = new Matrix();
        degrees = 0;

        animationGoes = true;
        colorGoes = true;

        preset = false;

        soundOn = PrefsDecoder.isSoundOn(context);
        soundOnButton = AssetStore.getBitmap(AssetConstants.MUSIC_ON, context);
        soundOffButton = AssetStore.getBitmap(AssetConstants.MUSIC_OFF, context);
        MusicPlayer.INSTANCE.enable(soundOn, context);
    }

    //FUNCTIONAL METHODS:

    //@SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!preset) preset(canvas);
        matrix.setRotate(degrees);
        normrad = ((canvas.getWidth() < canvas.getHeight()) ? (canvas.getWidth() / 5 * 2) : (canvas.getHeight() / 5 * 2));

        canvas.drawBitmap(Bitmap.createScaledBitmap(AssetStore.getBitmap(AssetConstants.BACKGROUND, this.getContext()), canvas.getWidth(), canvas.getHeight(), false), 0, 0, backgroungPaint);

        if (current.name.equals(AssetConstants.NONE)) {
            Random rnd = new Random();
            rad = (normrad/5*6)*(rnd.nextFloat()/32+1);
            canvas.drawBitmap(Bitmap.createScaledBitmap(current.body, (int) rad*2, (int) rad*2, false), canvas.getWidth()/2 - rad, canvas.getHeight()/2 - rad, paint);
        } else if (current.hitCount == 0) {
            int pred = ((canvas.getWidth() < canvas.getHeight()) ? (canvas.getWidth() / 5 * 3) : (canvas.getHeight() / 5 * 3));
            if (rad == 0) rad = normrad;
            rad += (rad >= pred) ? (0) : (1);
            int width, height;
            if (!current.name.equals(AssetConstants.SMTH_ELSE)) {
                width = current.content.getWidth();
                height = current.content.getHeight();
            } else {
                width = bestFitW;
                height = bestFitH;
            }

            if (colorGoes) {
                animation += 5;
                if (animation >= 255) {
                    colorGoes = false;
                    animation = 255;
                }
            } else {
                animation -= 5;
                if (animation <= 0) {
                    colorGoes = true;
                    animation = 0;
                }
            }
            ColorFilter filter = new PorterDuffColorFilter(Color.argb(255, 255 - animation, (int) (Math.asin((float)animation/255)/Math.PI/2*1020), animation), PorterDuff.Mode.SRC_ATOP);
            animationPaint.setColorFilter(filter);

            if (animationGoes) {
                animationCounter -= 1;
                animationGoes = (animationCounter > 10);
            } else {
                animationCounter += 1;
                animationGoes = (animationCounter >= 20);
            }
            canvas.drawBitmap(Bitmap.createBitmap((int) (rad*2* width /1000 + rad/animationCounter*2), (int) (rad*2* height /1000 + rad/animationCounter*2), Bitmap.Config.RGB_565), canvas.getWidth()/2 - rad* width /1000 - rad/animationCounter, canvas.getHeight()/2 - rad* height /1000 - rad/animationCounter, animationPaint);

            canvas.drawBitmap(Bitmap.createScaledBitmap(current.content, (int) rad*2* width /1000, (int) rad*2* height /1000, false), canvas.getWidth()/2 - rad* width /1000, canvas.getHeight()/2 - rad* height /1000, paint);
        } else if (pointMeasure == 0) {
            rad = normrad;
            canvas.drawBitmap(Bitmap.createScaledBitmap(current.body, (int) rad*2, (int) rad*2, false), canvas.getWidth()/2 - rad, canvas.getHeight()/2 - rad*3/2, paint);
            if (findPresent() > 0) canvas.drawBitmap(Bitmap.createScaledBitmap(current.present[findPresent() - 1], (int) rad*2, (int) rad*2, false), canvas.getWidth()/2 - rad, canvas.getHeight()/2 - rad*3/2, paint);
            canvas.drawBitmap(Bitmap.createScaledBitmap(current.top, (int) rad*2, (int) rad*2, false), canvas.getWidth()/2 - rad, canvas.getHeight()/2 - rad*3/2, paint);
            degrees = 0;
        } else {
            if ((rad < (canvas.getWidth() / 40 * 17)) && (rad < (canvas.getHeight() / 40 * 17))) {
                rad += pointMeasure;
                degrees += 0.75;
            }
            canvas.drawBitmap(Bitmap.createScaledBitmap(current.body, (int) rad * 2, (int) rad * 2, false), canvas.getWidth() / 2 - rad, canvas.getHeight() / 2 - rad*3/2, paint);
            if (findPresent() > 0) canvas.drawBitmap(Bitmap.createScaledBitmap(current.present[findPresent() - 1], (int) rad * 2, (int) rad * 2, false), canvas.getWidth() / 2 - rad, canvas.getHeight() / 2 - rad*3/2, paint);
            Bitmap bOutput = Bitmap.createBitmap(current.top, 0, 0, current.top.getWidth(), current.top.getHeight(), matrix, false);
            canvas.drawBitmap(Bitmap.createScaledBitmap(bOutput, (int) rad * 2, (int) rad * 2, false), canvas.getWidth() / 2 - rad, canvas.getHeight() / 2 - rad*3/2, paint);
        }



        canvas.drawBitmap(Bitmap.createScaledBitmap(soundOn ? soundOnButton : soundOffButton,  (int) normrad/3, (int) normrad/3, false), 0, canvas.getHeight() - normrad/3, paint);

        if ((!current.name.equals(AssetConstants.NONE)) && (!won)) {
            canvas.drawText(String.valueOf(current.hitCount), canvas.getWidth()/2, canvas.getHeight()/2 + finrad*3/2, textPaint);
            canvas.drawText(String.valueOf(current.hitCount), canvas.getWidth()/2, canvas.getHeight()/2 + finrad*3/2, liningPaint);
        } else if (won) {
            canvas.drawText("Твой " + presentNumber, canvas.getWidth()/2, canvas.getHeight()/2 - finrad*25/16, textPaint);
            canvas.drawText("Твой " + presentNumber, canvas.getWidth()/2, canvas.getHeight()/2 - finrad*25/16, liningPaint);
            canvas.drawText("подарок:", canvas.getWidth()/2, canvas.getHeight()/2 - finrad*25/16 - textPaint.ascent() + textPaint.descent(), textPaint);
            canvas.drawText("подарок:", canvas.getWidth()/2, canvas.getHeight()/2 - finrad*25/16 - liningPaint.ascent() + liningPaint.descent(), liningPaint);
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        if ((!current.name.equals(AssetConstants.NONE)) && ((event.getX() > (getWidth()/2 - rad)) && (event.getX() < (getWidth()/2 + rad)) && (event.getY() > (getHeight()/2 - rad)) && (event.getY() < (getHeight()/2 + rad)))) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //Log.e("TAG", "\nWidth: " + current.content.getWidth() + "\nHeight: " + current.content.getHeight() + "\n" );
                pointMeasure = 0;
                //System.out.println("up");
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                pointMeasure = 4;
                //System.out.println("down");
                current.hitCount -= ((current.hitCount > 0) ? (1) : (0));
                if ((current.hitCount == 0) && (!won)) {
                    won = true;
                    preset = false;
                    if (current.name.equals(AssetConstants.SMTH_ELSE)){
                        try {
                            BirthdayMain.saveImageToExternal("happy_birthday", current.filename, getContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    setHandler();
                }
            }
            return true;
        } else if ((event.getX() < normrad/3) && (event.getY() > (getHeight() - normrad/3))) {
            soundOn = !soundOn;
            PrefsDecoder.turnSound(getContext(), soundOn);
            System.out.println("The music was turned " + (soundOn ? "on." : "off."));
            MusicPlayer.INSTANCE.enable(soundOn, getContext());
            return false;
        } else {
            return false;
        }
    }

    public int findPresent() {
        int currDen = (int) Math.ceil((double) current.globalHitCount / (AssetConstants.COVER_count));
        return (int) (AssetConstants.COVER_count - Math.floor((double) current.hitCount / currDen));
    }

    private void preset(Canvas canvas) {
        finrad = ((canvas.getWidth() < canvas.getHeight()) ? (canvas.getWidth() / 40 * 17) : (canvas.getHeight() / 40 * 17));
        int font;
        if (!won) {
            font = (canvas.getWidth() < canvas.getHeight()) ? (canvas.getWidth() / 5) : (canvas.getHeight() / 5);
            textPaint.setColor(Color.RED);
        } else {
            font = (canvas.getWidth() < canvas.getHeight()) ? (canvas.getWidth() / 7) : (canvas.getHeight() / 7);
            textPaint.setColor(Color.BLACK);
        }
        textPaint.setTextSize(font);
        liningPaint.setTextSize(font);
        preset = true;
    }

    private void setHandler() {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                listener.onPresentGot();
            }
        };
        mainHandler.postDelayed(myRunnable, 3000);
    }





    public interface Listener {
        void onPresentGot();
    }
}
