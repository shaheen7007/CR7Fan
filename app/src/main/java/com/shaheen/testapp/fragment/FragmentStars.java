package com.shaheen.testapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shaheen.testapp.PrefManager;
import com.shaheen.testapp.R;
import com.shaheen.testapp.adapter.ProfileListAdapter;
import com.shaheen.testapp.databaseRef.StarsRef;
import com.shaheen.testapp.model.Profile;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentStars extends Fragment {

    int position;
    private PrefManager prefManager;
    private String selected_country = null;
    private ArrayList<Profile> profileArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProfileListAdapter profileListAdapter;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        FragmentStars tabFragment = new FragmentStars();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
        prefManager = PrefManager.getInstance(getActivity());
        selected_country = prefManager.getCountry();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ṁInitialisations(view);
        mGetDataFromFirebase();


    }

    private void ṁInitialisations(View view) {

        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(mLayoutManager);
        profileListAdapter = new ProfileListAdapter(profileArrayList, getActivity());
        recyclerView.setAdapter(profileListAdapter);
    }

    private void mGetDataFromFirebase() {
        profileArrayList.clear();
        StarsRef.getInstance(getActivity(), prefManager.getCountry()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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

    private void mShowInRecylerView() {
        profileListAdapter.notifyDataSetChanged();
    }
}
