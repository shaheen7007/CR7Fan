package com.shaheen.testapp.databaseRef;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.shaheen.testapp.Consts;

public class FeaturedRef {
    public static DatabaseReference getInstance(Context context) {
        return MainRef.getInstance(context).child(Consts.FEATURED).getRef();
    }
}
