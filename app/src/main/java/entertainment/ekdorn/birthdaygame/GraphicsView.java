package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by User on 01.04.2018.
 */

public class GraphicsView extends View {

    //private Bitmap [] present = new Bitmap[7];
    //private Bitmap top;

    //private int globalCount;
    //private int hitCount;

    private Present current;

    private int pointMeasure;

    private float rad, finrad;
    private Paint paint;
    private Paint textPaint;
    private float degrees; // top rotation degree

    private Matrix matrix;
    private Listener listener;

    //SETTERS:

    public void setListener (Listener listener) {
        this.listener = listener;
    }

    public void setPresent(Present actual) {
        current = actual;
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

        paint.setAntiAlias(true);

        textPaint.setARGB(200, 254, 0, 0);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(100);

        matrix = new Matrix();
        degrees = 0;
    }

    //FUNCTIONAL METHODS:

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        finrad = ((canvas.getWidth() < canvas.getHeight()) ? (canvas.getWidth() / 40 * 17) : (canvas.getHeight() / 40 * 17));
        matrix.setRotate(degrees);

        if (pointMeasure == 0) {
            rad = ((canvas.getWidth() < canvas.getHeight()) ? (canvas.getWidth() / 5 * 2) : (canvas.getHeight() / 5 * 2));
            canvas.drawBitmap(Bitmap.createScaledBitmap(current.present[findPresent()], (int) rad*2, (int) rad*2, false), canvas.getWidth()/2 - rad, canvas.getHeight()/2 - rad*3/2, paint);
            canvas.drawBitmap(Bitmap.createScaledBitmap(current.top, (int) rad*2, (int) rad*2, false), canvas.getWidth()/2 - rad, canvas.getHeight()/2 - rad*3/2, paint);
            degrees = 0;
        } else {
            if ((rad < (canvas.getWidth() / 40 * 17)) && (rad < (canvas.getHeight() / 40 * 17))) {
                rad += pointMeasure;
                degrees += 0.75;
            }
            canvas.drawBitmap(Bitmap.createScaledBitmap(current.present[findPresent()], (int) rad * 2, (int) rad * 2, false), canvas.getWidth() / 2 - rad, canvas.getHeight() / 2 - rad*3/2, paint);
            Bitmap bOutput = Bitmap.createBitmap(current.top, 0, 0, current.top.getWidth(), current.top.getHeight(), matrix, true);
            canvas.drawBitmap(Bitmap.createScaledBitmap(bOutput, (int) rad * 2, (int) rad * 2, false), canvas.getWidth() / 2 - rad, canvas.getHeight() / 2 - rad*3/2, paint);
        }

        canvas.drawText(String.valueOf(current.hitCount), canvas.getWidth()/2, canvas.getHeight()/2 + finrad*3/2, textPaint);

        invalidate();
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        if (Math.sqrt((getWidth()/2 - event.getX()) * (getWidth()/2 - event.getX()) + (getHeight()/2 - rad/2 - event.getY()) * (getHeight()/2 - rad/2 - event.getY())) < rad) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                pointMeasure = 0;
                System.out.println("up");
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                pointMeasure = 4;
                System.out.println("down");
                current.hitCount -= ((current.hitCount > 0) ? (1) : (0));
                if (current.hitCount == 0) {
                    listener.onPresentGot();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public int findPresent() {
        int currDen = (int) Math.ceil((double) current.globalHitCount / 6);
        System.out.println(current.globalHitCount);
        return (int) (5 - Math.floor((double) current.hitCount / currDen));
    }



    public interface Listener {
        void onPresentGot();
    }
}
