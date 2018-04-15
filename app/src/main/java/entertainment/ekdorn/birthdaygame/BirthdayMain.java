package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import entertainment.ekdorn.birthdaygame.assetsWorking.AssetConstants;
import entertainment.ekdorn.birthdaygame.assetsWorking.AssetStore;
import entertainment.ekdorn.birthdaygame.assetsWorking.PrefsDecoder;
import entertainment.ekdorn.birthdaygame.assetsWorking.PrivateConstants;
import entertainment.ekdorn.birthdaygame.structureElements.Present;
import entertainment.ekdorn.birthdaygame.structureElements.RetainDialog;

public class BirthdayMain extends AppCompatActivity {

    public static final String appSign = "BIRTHDAY_GAME";
    public static final String folderSign = "Happy birthday 2018";
    private GraphicsView main;

    private boolean newGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AssetStore.loadAll(this);

        PrefsDecoder.setAlarm(this, true);

        Present last = PrefsDecoder.loadGame(this);
        newGame = (last.name.equals(AssetConstants.NONE));

        main = new GraphicsView(this);
        main.setPresent(newGame ? new Present(AssetConstants.NONE, this) : last);
        main.setListener(() -> {
            String newPresent;
            String newTitle;
            String newButton;
            boolean endOfLine;
            switch (PrefsDecoder.howManyPresents(this)) {
                case 1:
                    newPresent = AssetConstants.SPECIAL;
                    newTitle = "Это было просто. Другие подарки упакованы лучше. Переходим к следующему?\n\nP.S. За получением всех подарков можно (и нужно) обращаться ко мне лично.\n\nP.P.S. Я знаю, на каком месте ты остановилась...";
                    newButton = "Конечно!";
                    endOfLine = false;
                    break;
                case 2:
                    newPresent = AssetConstants.CARDBOARD;
                    newTitle = "Если честно, это всё. Надеюсь, тебе не надоело? Если хочешь, можешь открыть последний, третий, но предупреждаю сразу: он не так хорош, как эти два...\nБудешь смотреть?";
                    newButton = "Давай.";
                    endOfLine = false;
                    break;
                case 3:
                    newPresent = AssetConstants.SMTH_ELSE;
                    newTitle = "Ну вот теперь точно всё!\nСпасибо за игру. Я очень ценю твоё внимание и время в день рождения. Если ты дошла до сюда, значит, создание всей этой программы полностью оправдано. Have great fun!\n\nP.S. подарков больше нет, так, мелочь всякая. Интересует?";
                    newButton = "Ну, что там такое?";
                    endOfLine = true;
                    break;
                default:
                    newPresent = AssetConstants.SMTH_ELSE;
                    newTitle = "Я сохранил эту картинку в галерею";
                    newButton = "Дальше.";
                    endOfLine = false;
                    break;
            }
            applyDialog("Поздравляю!", newTitle, newButton, appSign, newPresent + " got. Get ready.", newPresent, endOfLine);
        });
        setContentView(main);
    }

    @Override
    protected void onPause() {
        PrefsDecoder.saveGame(this, main.getCurrent());
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e("TAG", "onResume: " + newGame );

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            main.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        if ((newGame) && (Build.VERSION.SDK_INT < 20? powerManager.isScreenOn() : powerManager.isInteractive())) {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            Runnable starter = new Runnable() {
                @Override
                public void run() {
                    applyDialog("Привет Маша!", "С днём рождения!\nЯ приготовил тебе несколько подарков (всего три) и завернул их в эту программу. Ты можешь открыть когда и сколько захочешь. Удачи!\n\uD83D\uDC21, второй лучший...",
                            "Давай сюда всё!", appSign, "she started", AssetConstants.BOOK, false);
                    newGame = false;
                }
            };
            mainHandler.postDelayed(starter, 4000);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if ((hasFocus) && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)) {
            main.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }





    public void applyDialog(String dialogTitle, String dialogMessage, String onButton, String emailTitle, String emailBody, String nextPresent, boolean isEndOfLine) {
        RetainDialog ret = new RetainDialog(BirthdayMain.this, this);
        ret.setTitle(dialogTitle);
        ret.setMessage(dialogMessage);
        ret.setCanceledOnTouchOutside(false);
        ret.setCancelable(false);
        ret.setButton(DialogInterface.BUTTON_POSITIVE, onButton, (DialogInterface.OnClickListener) null);
        if (isEndOfLine) {
            ret.setButton(DialogInterface.BUTTON_NEUTRAL, "У меня есть что сказать лично", (DialogInterface.OnClickListener) null);
        }
        ret.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                main.setPresent(new Present(nextPresent, BirthdayMain.this));
            }
        });
        ret.show();

        ret.getButton(RetainDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefsDecoder.obtainPresent(BirthdayMain.this);
                BackgroundMail.newBuilder(BirthdayMain.this)
                        .withUsername(PrivateConstants.appEMailLogin)
                        .withPassword(PrivateConstants.appEMailPassword)
                        .withMailto(PrivateConstants.myEMail)
                        .withType(BackgroundMail.TYPE_PLAIN)
                        .withSubject(emailTitle)
                        .withBody(emailBody)
                        .withProcessVisibility(false)
                        .withSendingMessageSuccess(null)
                        .withSendingMessageSuccess(null)
                        .send();
                ret.dismiss();
            }
        });
        ret.getButton(RetainDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/pseusys"));
                startActivity(telegram);
            }
        });
    }



    public static void saveImageToExternal(String imgName, String bitmap, Context context) throws IOException { //Create Path to save Image
        long millis = System.currentTimeMillis();
        Thread saver = new Thread() {
            @Override
            public void run() {
                try{
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + folderSign); //Creates app specific folder
                    System.out.println(path.getAbsolutePath());
                    path.mkdirs();
                    File imageFile = new File(path, imgName + ".png"); // Imagename.png
                    FileOutputStream out = new FileOutputStream(imageFile);
                    InputStream ims = context.getAssets().open(bitmap);

                    byte[] buffer = new byte[1024]; // Adjust if you want
                    int bytesRead;
                    while ((bytesRead = ims.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    //bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                    out.flush();
                    out.close();

                    // Tell the media scanner about the new file so that it is
                    // immediately available to the user.
                    MediaScannerConnection.scanFile(context, new String[] { imageFile.getAbsolutePath() }, null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
                } catch(Exception e) {
                    Log.e("ERROR", "image can not be saved: ", e);;
                }
            }
        };
        saver.start();
        Log.e("TAG", "saveImageToExternal: " + (System.currentTimeMillis() - millis));
    }
}
