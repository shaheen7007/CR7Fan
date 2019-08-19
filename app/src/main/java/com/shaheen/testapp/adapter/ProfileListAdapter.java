package com.shaheen.testapp.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shaheen.testapp.Consts;
import com.shaheen.testapp.R;
import com.shaheen.testapp.activity.ProfileActivity;
import com.shaheen.testapp.model.Profile;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.MyViewHolder> {

    private List<Profile> profileList;
    private Context context;

    RequestOptions options;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePic;
        TextView user_id, followers;
        CardView viewItem;

        public MyViewHolder(View view) {
            super(view);
            user_id = view.findViewById(R.id.user_id);
            followers = view.findViewById(R.id.followers);
            profilePic = view.findViewById(R.id.profile_image);
            viewItem = view.findViewById(R.id.view_item);
            options = new RequestOptions();
            options.centerCrop();


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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Profile profile = profileList.get(position);
        holder.user_id.setText(profile.getTiktok_id());
        holder.followers.setText(profile.getFollowers());

        Glide.with(context)
                .load(profile.getImg_url())
                .apply(options)
                .into(holder.profilePic);

        holder.viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(Consts.SELECTED_PROFILE, profile);

                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context, holder.profilePic, "propic");

                context.startActivity(intent, options.toBundle());

            }
        });

    }


    @Override
    public int getItemCount() {
        return profileList.size();
    }
}