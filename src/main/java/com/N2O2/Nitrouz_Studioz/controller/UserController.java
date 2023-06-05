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
    public String loggedInProfilePage(Model model,
                                      RedirectAttributes redirectAttributes){
        profileEntity = loggedInUser();
        updateProfile = false;
        model.addAttribute("profileEntity", profileEntity);
        model.addAttribute("updateProfile", updateProfile);

        if(redirectAttributes.asMap().get("editSuccess") != null) {
            model.addAttribute("editSuccess", model.asMap().get("editSuccess"));
        }

        return "profile";
    }

    @RequestMapping("/edit_profile")
    public String editProfile(Model model,
                              RedirectAttributes redirectAttributes){
        profileEntity = loggedInUser();
        updateProfile = true;
        model.addAttribute("profileEntity", profileEntity);
        model.addAttribute("updateProfile", updateProfile);

        if(redirectAttributes.asMap().get("editError") != null) {
            model.addAttribute("editError", model.asMap().get("editError"));
        }

        return "profile";
    }

    @RequestMapping("/updateProfile")
    public String updateProfile(RedirectAttributes redirectAttributes,
                                @RequestParam(name = "first_name") String first_name,
                                @RequestParam(name = "last_name") String last_name,
                                @Valid ProfileEntity profileEntity,
                                BindingResult bindingResult){
        ProfileEntity currentProfile = loggedInUser();
        if(bindingResult.hasErrors()){
            if(!(profileEntity.getPassword() == null)
                    && !profileEntity.getPassword().equals(currentProfile.getPassword())){
                redirectAttributes.addFlashAttribute("editError", "Password's don't match. Error occurred while updating profile.");
                return "redirect:/loggedInUser/edit_profile";
            }
            if(profileEntity.getEmail().isEmpty()){
                redirectAttributes.addFlashAttribute("editError", "Email can't be blank");
                return "redirect:/loggedInUser/edit_profile";
            }

            profileEntity.setPassword(currentProfile.getPassword());
        }

        if(first_name.isEmpty() || last_name.isEmpty()){
            redirectAttributes.addFlashAttribute("editError", "Please fill out all required fields.");
            return "redirect:/loggedInUser/edit_profile";
        }
        profileEntity.setId(currentProfile.getId());
        profileEntity.setComments(currentProfile.isComments());
        profileEntity.setLikes(currentProfile.isLikes());
        profileEntity.setFollows(currentProfile.isFollows());
        profileEntity.setEnabled(currentProfile.isEnabled());
        profileEntity.setGeneral(currentProfile.isGeneral());
        profileEntity.setRoles(currentProfile.getRoles());

        profileService.updateProfileInfo(profileEntity);
        updateProfile = false;
        redirectAttributes.addFlashAttribute("editSuccess", "Profile successfully updated!");
        return "redirect:/loggedInUser/profile";
    }

    private ProfileEntity loggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return profileService.findProfileByEmail(auth.getName());
    }
}

