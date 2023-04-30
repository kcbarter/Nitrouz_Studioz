package com.N2O2.Nitrouz_Studioz.controller;

import com.N2O2.Nitrouz_Studioz.model.profile.ProfileEntity;
import com.N2O2.Nitrouz_Studioz.model.service.MemberService;
import com.N2O2.Nitrouz_Studioz.model.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/loggedInUser")
@Controller
public class UserController {
    @Autowired
    ProfileService profileService;
    @Autowired
    MemberService memberService;

    private ProfileEntity profileEntity;
    private final boolean loggedIn = true;
    private final boolean loggedOut = false;

    private final boolean activeElement = true;
    private final boolean notActive = false;

    private boolean updateProfile;

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
        updateProfile = false;
        model.addAttribute("profileEntity", profileEntity);
        model.addAttribute("updateProfile", updateProfile);

        return "profile";
    }

    @RequestMapping("/edit_profile")
    public String editProfile(Model model){
        profileEntity = loggedInUser();
        updateProfile = true;
        model.addAttribute("profileEntity", profileEntity);
        model.addAttribute("updateProfile", updateProfile);

        return "profile";
    }

    @RequestMapping("/updateProfile")
    public String updateProfile(RedirectAttributes redirectAttributes,
                                ProfileEntity profileEntity,
                                @RequestParam(name = "first_name") String first_name,
                                @RequestParam(name = "last_name") String last_name,
                                BindingResult bindingResult){
        if(first_name.isEmpty() || last_name.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Please fill out all required fields.");
            redirectAttributes.addFlashAttribute("profileEntity", profileEntity);
            return "redirect:/loggedInUser/edit_profile";
        }
        ProfileEntity currentProfile = loggedInUser();
        profileEntity.setPassword(currentProfile.getPassword());
        return performUpdateOfProfile(redirectAttributes, profileEntity, bindingResult);
    }

    private String performUpdateOfProfile(RedirectAttributes redirectAttributes,
                                        @Valid ProfileEntity profileEntity,
                                        BindingResult bindingResult){
        System.out.println(profileEntity.getPassword());
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("error", "Errors in form submission. Please try again.");
            redirectAttributes.addFlashAttribute("profileEntity", profileEntity);
            return "redirect:/loggedInUser/edit_profile";
        }
        profileService.updateProfileInfo(profileEntity);
        updateProfile = false;
        redirectAttributes.addFlashAttribute("success", "Profile successfully updated!");
        return "redirect:/loggedInUser/profile";
    }

    private ProfileEntity loggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return profileService.findProfileByEmail(auth.getName());
    }
}

