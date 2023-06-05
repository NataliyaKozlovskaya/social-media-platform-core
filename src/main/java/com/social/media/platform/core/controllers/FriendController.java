package com.social.media.platform.core.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.social.media.platform.core.services.FriendshipService;

@RestController
public class FriendController {

    private final FriendshipService friendshipService;

    public FriendController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

//


}
