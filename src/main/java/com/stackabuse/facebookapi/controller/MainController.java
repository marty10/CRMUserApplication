package com.stackabuse.facebookapi.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
	private static final String PAGE_FIELDS = "id,name,category";
    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public MainController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String feed(Model model) {

        if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        User userProfile = facebook.userOperations().getUserProfile();
        model.addAttribute("userProfile", userProfile);
        return "user";
    }

    @RequestMapping(value = "likes", method = RequestMethod.GET)
    public String likes(Model model) {

        if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        PagedList<Page> likes = facebook.fetchConnections("me", "likes", Page.class, PAGE_FIELDS);
        model.addAttribute("likes", likes);
     

        return "likes";
    }
}
