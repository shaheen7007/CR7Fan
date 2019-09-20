package com.shaheen.testapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.SignInButton;
import com.shaheen.testapp.Consts;
import com.shaheen.testapp.PrefManager;
import com.shaheen.testapp.R;
import com.shaheen.testapp.dialog.LoginToContinueDialog;
import com.shaheen.testapp.model.Profile;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    Profile profile;
    CircleImageView profilePic;
    TextView TVname, TVfollowers;
    Button BTNopen;
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile = getProfile();
        init();
    }

    private void init() {
        prefManager = new PrefManager(this);
        profilePic = findViewById(R.id.profile_image);
        TVname = findViewById(R.id.name);
        BTNopen = findViewById(R.id.open);
        TVfollowers = findViewById(R.id.followers);

        Glide.with(ProfileActivity.this)
                .load(profile.getImg_url())
                .into(profilePic);

        TVname.setText(profile.getTiktok_id());
        TVfollowers.setText(profile.getFollowers());


        BTNopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefManager.getIsLoggedIn()) { // check also is not already followed

                    if (appInstalledOrNot(Consts.TIKTOK_PACKAGE)) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.tiktok.com/h5/share/usr/8677296.html")));

                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Consts.TIKTOK_PACKAGE)));
                    }
                } else {
                    showGoogleSignInAlertDialog();
                }
            }
        });


    }

    private void showGoogleSignInAlertDialog() {
        LoginToContinueDialog alert = new LoginToContinueDialog();
        alert.showDialog(ProfileActivity.this);
    }

    private Profile getProfile() {

        return getIntent().getParcelableExtra(Consts.SELECTED_PROFILE);

    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


}
