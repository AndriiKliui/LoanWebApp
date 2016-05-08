package com.finanse.controller;

import com.finanse.model.User;
import com.finanse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "user/current", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Secured({"USER", "ADMIN"})
    public User currentAccount(Principal principal) {
        Assert.notNull(principal);
        return userRepository.findOneByEmail(principal.getName());
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Secured("ADMIN")
    public User user(@PathVariable("id") Long id) {
        return userRepository.findOne(id);
    }
}
