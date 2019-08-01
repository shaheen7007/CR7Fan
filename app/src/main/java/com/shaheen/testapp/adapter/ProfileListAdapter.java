package com.shaheen.testapp.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shaheen.testapp.R;
import com.shaheen.testapp.model.Profile;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.MyViewHolder> {

    private List<Profile> profileList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilePic;
        public TextView user_id, followers;

        public MyViewHolder(View view) {
            super(view);
            user_id = view.findViewById(R.id.user_id);
            followers = view.findViewById(R.id.followers);
            profilePic = view.findViewById(R.id.imgGallery);
        }
    }

    public ProfileListAdapter(List<Profile> profiles, Context context) {
        this.profileList = profiles;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Profile profile = profileList.get(position);
        holder.user_id.setText(profile.getTiktok_id());
        holder.followers.setText(profile.getFollowers());
      //  Glide.with(context).load("http://hanassets.nd.gov/images/product/test.png").dontAnimate().into(holder.profilePic);
        Picasso.with(context).load("http://hanassets.nd.gov/images/product/test.png").into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
}