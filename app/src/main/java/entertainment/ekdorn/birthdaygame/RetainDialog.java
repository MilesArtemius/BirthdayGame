package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by User on 20.07.2017.
 */

public class RetainDialog extends AlertDialog {
    View parent;

    public Button NewGameButton;
    public Button ContinueButton;
    public Button AuthorsButton;
    public Button AchievementsButton;
    public CheckBox QuickModeCheckBox;

    public Button getNewGameButton() {
        return NewGameButton;
    }

    public Button getContinueButton() {
        return ContinueButton;
    }

    public Button getAuthorsButton() {
        return AuthorsButton;
    }

    public Button getAchievementsButton() {
        return AchievementsButton;
    }

    public CheckBox getQuickModeCheckBox() {
        return QuickModeCheckBox;
    }

    protected RetainDialog(@NonNull Context context) {
        super(context);
        parent = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        NewGameButton = (Button) parent.findViewById(R.id.new_game_button);
        ContinueButton = (Button) parent.findViewById(R.id.continue_button);
        AuthorsButton = (Button) parent.findViewById(R.id.authors_and_developers_button);
        AchievementsButton = (Button) parent.findViewById(R.id.achievements_button);
        QuickModeCheckBox = (CheckBox) parent.findViewById(R.id.quick_mode_check_box);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            parent.setSystemUiVisibility(uiOptions);
        } else {
            int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

            this.getOwnerActivity().getWindow().getDecorView().setSystemUiVisibility(mUIFlag);
            this.getOwnerActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }*/

        this.setContentView(parent);
    }
}
