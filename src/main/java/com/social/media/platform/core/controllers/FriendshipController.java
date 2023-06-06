package com.social.media.platform.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.social.media.platform.core.services.FriendshipService;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping("/user/{userId}/friend/{friendId}")
    public void askFriendship(@PathVariable("userId") Integer userId,
                              @PathVariable("friendId") Integer friendId) {
        friendshipService.askFriendship(userId, friendId);
    }

    @PatchMapping("/{askId}/{ansId}/{flag}")
    public void answerFriendship(@PathVariable("askId") Integer askId,
                                 @PathVariable("ansId") Integer ansId,
                                 @PathVariable("flag") Boolean flag) {
        friendshipService.answerFriendship(askId, ansId, flag);
    }

    @DeleteMapping("/{friendshipId1}/{friendshipId2}")
    public void deleteFriendship(@PathVariable("friendshipId1") Integer friendshipId1,
                                 @PathVariable("friendshipId2") Integer friendshipId2) {
        friendshipService.deleteFriendship(friendshipId1, friendshipId2);
    }

    @DeleteMapping("subscription/{friendshipId}")
    public void deleteSubscription(@PathVariable("friendshipId") Integer friendshipId) {
        friendshipService.deleteSubscription(friendshipId);
    }

}
