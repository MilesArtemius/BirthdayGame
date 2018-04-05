package entertainment.ekdorn.birthdaygame;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

public class BirthdayMain extends AppCompatActivity {

    public static String appSign = "BIRTHDAY_GAME";
    private GraphicsView main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Present last = PrefsDecoder.loadGame(this);
        boolean newGame = (last.name.equals(PresentNames.NONE));

        main = new GraphicsView(this);
        main.setPresent(newGame ? new Present(PresentNames.NONE, this) : last);
        main.setListener(() -> {
            applyDialog("You won!", "", "get the present",appSign, "first one", PresentNames.SPECIAL);
        });
        setContentView(main);

        if (newGame) {
            applyDialog("Hi Masha!", "This is a game to get your birthday presents",
                    "start!",appSign, "she started", PresentNames.BOOK);
        }
    }

    @Override
    protected void onPause() {
        PrefsDecoder.saveGame(this, main.getCurrent());
        super.onPause();
    }





    public void applyDialog(String dialogTitle, String dialogMesage, String onButton, String emailTitle, String emailBody, String nextPresent) {
        RetainDialog ret = new RetainDialog(BirthdayMain.this);
        ret.setTitle(dialogTitle);
        ret.setMessage(dialogMesage);
        ret.setCanceledOnTouchOutside(false);
        ret.setButton(DialogInterface.BUTTON_POSITIVE, onButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BackgroundMail.newBuilder(BirthdayMain.this)
                        .withUsername("total.noreply.notifier@gmail.com")
                        .withPassword("multipassword")
                        .withMailto("shveitsar215@gmail.com")
                        .withType(BackgroundMail.TYPE_PLAIN)
                        .withSubject(emailTitle)
                        .withBody(emailBody)
                        .withProcessVisibility(false)
                        .withSendingMessageSuccess(null)
                        .withSendingMessageSuccess(null)
                        .send();
            }
        });
        ret.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                main.setPresent(new Present(nextPresent, BirthdayMain.this));
            }
        });
        ret.show();
    }
}
