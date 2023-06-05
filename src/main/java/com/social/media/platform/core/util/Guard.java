package com.social.media.platform.core.util;

import com.social.media.platform.core.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class Guard {
    public boolean checkUserId(Authentication authentication, Integer id) {
        User principal = (User) authentication.getPrincipal();
        return id.equals(principal.getId());
    }
}

