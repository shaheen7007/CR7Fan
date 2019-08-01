package com.shaheen.testapp.databaseRef;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.shaheen.testapp.Consts;

public class StarsRef {
    public static DatabaseReference getInstance(Context context,String country) {
        return MainRef.getInstance(context).child(Consts.STARS).child(country);
    }
}
