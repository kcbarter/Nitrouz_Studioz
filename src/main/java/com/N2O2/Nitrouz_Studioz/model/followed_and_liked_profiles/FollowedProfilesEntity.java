package com.N2O2.Nitrouz_Studioz.model.followed_and_liked_profiles;


import javax.persistence.*;

@Entity
@Table(name = "followed_profiles")
@IdClass(FollowedProfilesId.class)
public class FollowedProfilesEntity {
    @Column(name = "followed_id")
    @Id
    private long followed_Id;

    @Column(name = "follower_id")
    @Id
    private long follower_Id;

    public FollowedProfilesEntity(){

    }

    public long getFollowed_Id() {
        return followed_Id;
    }

    public void setFollowed_Id(long followed_Id) {
        this.followed_Id = followed_Id;
    }

    public long getFollower_Id() {
        return follower_Id;
    }

    public void setFollower_Id(long follower_Id) {
        this.follower_Id = follower_Id;
    }
}
