package com.N2O2.Nitrouz_Studioz.model.profile;

import com.N2O2.Nitrouz_Studioz.model.role.RoleEntity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profileID")
    private Long id;
    @Column(name = "profile_pic")
    private String profilePic;
    @Column(name = "profile_name")
    private String profileName;
    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;
    @NotNull
    @Size(min=3)
    @Column(name = "email")
    private String email;
    @Column(name = "about")
    private String about;
    @Column(name = "phone")
    private String phone;
    @NotNull
    @Size(min=8)
    @Column(name = "password")
    private String password;
    @Column(name = "likes")
    private boolean likes;
    @Column(name = "comments")
    private boolean comments;
    @Column(name = "follows")
    private boolean follows;
    @Column(name = "general")
    private boolean general;
    @Column(name = "enabled")
    private boolean enabled;
    @ManyToMany
    @JoinTable(name = "profile_role", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    public ProfileEntity(String profilePic, String profileName, String first_name, String last_name, String email, String about, String phone,
        String password, boolean likes, boolean comments, boolean follows, boolean general, boolean enabled){
        this.profilePic = profilePic;
        this.profileName = profileName;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.about = about;
        this.phone = phone;
        this.password = password;
        this.likes = likes;
        this.comments = comments;
        this.follows = follows;
        this.general = general;
        this.enabled = enabled;
    }

    public ProfileEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setName(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name(){ return last_name;}

    public void  setLast_name(String last_name){
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }

    public boolean isComments() {
        return comments;
    }

    public void setComments(boolean comments) {
        this.comments = comments;
    }

    public boolean isFollows() {
        return follows;
    }

    public void setFollows(boolean follows) {
        this.follows = follows;
    }

    public boolean isGeneral() {
        return general;
    }

    public void setGeneral(boolean general) {
        this.general = general;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
