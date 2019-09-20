package com.shaheen.testapp.databaseRef;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.shaheen.testapp.Consts;

import androidx.annotation.NonNull;

public class TiktokIDsRef {
    public static DatabaseReference getInstance(Context context) {
        return MainRef.getInstance(context).child(Consts.TIKTOK_IDS).getRef();
    }

}
