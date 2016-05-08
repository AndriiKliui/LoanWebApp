package com.finanse.service;

import com.finanse.exception.ServiceLayerException;
import com.finanse.model.User;
import com.finanse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        if (Objects.nonNull(userRepository.findOneByEmail(user.getEmail()))) {
            throw new ServiceLayerException("Such user is already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(user);
    }

    public void signin(User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }

    private Authentication authenticate(User user) {
        return new UsernamePasswordAuthenticationToken(createUser(user), null, Collections.singleton(createAuthority(user)));
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singleton(createAuthority(user)));
    }

    private GrantedAuthority createAuthority(User user) {
        return new SimpleGrantedAuthority(user.getRole().getValue());
    }

}
