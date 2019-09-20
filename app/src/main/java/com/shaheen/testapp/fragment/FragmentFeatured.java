package com.shaheen.testapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fxn.pix.Pix;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shaheen.testapp.PrefManager;
import com.shaheen.testapp.R;
import com.shaheen.testapp.adapter.ProfileListAdapter;
import com.shaheen.testapp.databaseRef.FeaturedRef;
import com.shaheen.testapp.model.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentFeatured extends Fragment {

    int position;
    BottomSheet bottomSheet;
    private PrefManager prefManager;
    private ArrayList<Profile> profileArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProfileListAdapter profileListAdapter;
    private Button BTNbeAstar;
    ArrayList<String> returnValue = new ArrayList<>();


    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        FragmentFeatured tabFragment = new FragmentFeatured();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_featured, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ṁInitialisations(view);
        mGetDataFromFirebase();
    }


    private void mGetDataFromFirebase() {

        FeaturedRef.getInstance(getActivity()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profileArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Profile user = snapshot.getValue(Profile.class);
                    profileArrayList.add(user);
                }
                mShowInRecylerView();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("abcd", "Failed to read value.", error.toException());
            }
        });


    }

    private void ṁInitialisations(View view) {

        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(mLayoutManager);
        profileListAdapter = new ProfileListAdapter(profileArrayList, getActivity());
        recyclerView.setAdapter(profileListAdapter);
        recyclerView.setHasFixedSize(true);
        bottomSheet = BottomSheet.newInstance();
        BTNbeAstar = view.findViewById(R.id.btn);

        BTNbeAstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.show(getActivity().getSupportFragmentManager(),
                        "be a star");
            }
        });

    }

    private void mShowInRecylerView() {
        Collections.reverse(profileArrayList);
        profileListAdapter.notifyDataSetChanged();
    }





}