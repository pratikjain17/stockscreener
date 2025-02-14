package com.pratik.stockscreener.service.impl;

import com.pratik.stockscreener.model.User;
import com.pratik.stockscreener.repository.UserRepository;
import com.pratik.stockscreener.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return null;
    }
}
