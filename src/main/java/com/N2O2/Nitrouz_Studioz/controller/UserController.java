package com.N2O2.Nitrouz_Studioz.controller;

import com.N2O2.Nitrouz_Studioz.model.profile.ProfileEntity;
import com.N2O2.Nitrouz_Studioz.model.service.MemberService;
import com.N2O2.Nitrouz_Studioz.model.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/LoggedInUser")
@Controller
public class UserController {
    @Autowired
    ProfileService profileService;
    @Autowired
    MemberService memberService;

    private ProfileEntity profileEntity;
    private boolean loggedIn = true;
    private boolean loggedOut = false;

    private boolean activeElement = true;
    private boolean notActive = false;

    @GetMapping("/success")
    public String logInAttmpt(Model model){
        profileEntity = loggedInUser();
        model.addAttribute("profileEntity", profileEntity);
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("loggedOut", loggedOut);
        model.addAttribute("homeActive", activeElement);
        model.addAttribute("aboutActive", notActive);
        model.addAttribute("membersActive", notActive);
        return "index";
    }

    @RequestMapping("/index")
    public String loggedInIndex(Model model){
        profileEntity = loggedInUser();
        model.addAttribute("profileEntity", profileEntity);
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("loggedOut", loggedOut);
        model.addAttribute("homeActive", activeElement);
        model.addAttribute("aboutActive", notActive);
        model.addAttribute("membersActive", notActive);
        return "index";
    }

    @RequestMapping("/about")
    public String loggedInAbout(Model model){
        profileEntity = loggedInUser();
        model.addAttribute("profileEntity", profileEntity);
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("loggedOut", loggedOut);
        model.addAttribute("homeActive", notActive);
        model.addAttribute("aboutActive", activeElement);
        model.addAttribute("membersActive", notActive);
        return "about";
    }

    @RequestMapping("/members")
    public String loggedInMembers(Model model){
        List<ProfileEntity> profiles;
        profiles = memberService.getAllProfiles();
        profileEntity = loggedInUser();
        List<ProfileEntity> likedProfiles = memberService.getProfilesLikedByUser(profileEntity);
        List<ProfileEntity> followedProfiles = memberService.getProfilesFollowedByUser(profileEntity);
        model.addAttribute("profileEntity", profileEntity);
        model.addAttribute("liked_profiles", likedProfiles);
        model.addAttribute("followed_profiles", followedProfiles);
        model.addAttribute("profiles", profiles);
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("loggedOut", loggedOut);
        model.addAttribute("homeActive", notActive);
        model.addAttribute("aboutActive", notActive);
        model.addAttribute("membersActive", activeElement);

        return "members";
    }

    @RequestMapping("/profile")
    public String loggedInProfilePage(Model model){
        profileEntity = loggedInUser();
        model.addAttribute("profileEntity", profileEntity);

        return "profile";
    }

    private ProfileEntity loggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return profileService.findProfileByEmail(auth.getName());
    }
}

