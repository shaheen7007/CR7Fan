package com.shaheen.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable{
    String tiktok_id,followers,img_url,profileURL;
    String no_of_followers;

    public Profile() {
    }

    protected Profile(Parcel in) {
        tiktok_id = in.readString();
        followers = in.readString();
        img_url = in.readString();
        no_of_followers = in.readString();
    }


    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getTiktok_id() {
        return tiktok_id;
    }

    public void setTiktok_id(String tiktok_id) {
        this.tiktok_id = tiktok_id;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNo_of_followers() {
        return no_of_followers;
    }

    public void setNo_of_followers(String no_of_followers) {
        this.no_of_followers = no_of_followers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tiktok_id);
        dest.writeString(followers);
        dest.writeString(img_url);
        dest.writeString(profileURL);
        dest.writeString(no_of_followers);
    }
}
