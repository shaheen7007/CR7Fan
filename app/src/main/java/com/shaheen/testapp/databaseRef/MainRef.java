package com.shaheen.testapp.databaseRef;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shaheen.testapp.AppCalss;

public class MainRef  {
    private static DatabaseReference mDatabase;

    public static DatabaseReference getInstance(Context context) {
        if (mDatabase==null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return mDatabase;
    }


}
