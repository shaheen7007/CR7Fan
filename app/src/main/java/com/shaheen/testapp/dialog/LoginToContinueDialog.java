package com.shaheen.testapp.dialog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.gms.common.SignInButton;
import com.shaheen.testapp.PrefManager;
import com.shaheen.testapp.R;

public class LoginToContinueDialog {
    PrefManager prefManager;

    public void showDialog(Activity activity) {
        prefManager = new PrefManager(activity);
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_google_sign_in);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        SignInButton dialogButton = dialog.findViewById(R.id.sign_in_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setIsLoggedIn(true);
            }
        });

        dialog.show();

    }
}