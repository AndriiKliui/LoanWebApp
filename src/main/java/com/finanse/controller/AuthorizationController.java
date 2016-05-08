package com.finanse.controller;


import com.finanse.dto.SignupFormScreenDto;
import com.finanse.model.User;
import com.finanse.service.UserService;
import com.finanse.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthorizationController {
    private static final String SIGNUP_VIEW_NAME = "signup/signup";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "signin")
    public String signin() {
        return "signin/signin";
    }

    @RequestMapping(value = "signup")
    public String signup(Model model) {
        model.addAttribute(new SignupFormScreenDto());
        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute SignupFormScreenDto signupForm, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return SIGNUP_VIEW_NAME;
        }
        User user = userService.save(signupForm.createAccount());
        userService.signin(user);
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
        MessageHelper.addSuccessAttribute(ra, "signup.success");
        return "redirect:/";
    }

}
