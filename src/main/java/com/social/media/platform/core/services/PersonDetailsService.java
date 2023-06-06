package com.social.media.platform.core.services;

import com.social.media.platform.core.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social.media.platform.core.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
public class PersonDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public PersonDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
           throw new UsernameNotFoundException("User not found");
        }
        return (UserDetails) user;//?????просто user TODO
    }
}

