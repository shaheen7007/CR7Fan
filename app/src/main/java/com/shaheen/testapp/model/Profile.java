package com.shaheen.testapp.model;

public class Profile {
    String tiktok_id,followers,img_url;
    String no_of_followers;

    public Profile() {
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
}
