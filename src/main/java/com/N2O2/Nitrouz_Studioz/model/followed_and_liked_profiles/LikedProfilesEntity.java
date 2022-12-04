package com.N2O2.Nitrouz_Studioz.model.followed_and_liked_profiles;

import javax.persistence.*;

@Entity
@Table(name = "liked_profiles")
@IdClass(LikedProfilesId.class)
public class LikedProfilesEntity {
    @Column(name = "liked_id")
    @Id
    private long liked_Id;

    @Column(name = "liker_Id")
    @Id
    private long liker_Id;

    public LikedProfilesEntity(){

    }

    public long getLiked_Id(){
        return liked_Id;
    }

    public void setLiked_Id(long liked_Id){
        this.liked_Id = liked_Id;
    }

    public long getLiker_Id(){
        return liker_Id;
    }

    public void setLiker_Id(long liker_Id){
        this.liker_Id = liker_Id;
    }
}
