package com.social.media.platform.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.social.media.platform.core.services.FriendshipService;

@Tag(name = "Friendship controller", description = "Allows to create friendship, subscriptions and delete its")
@RestController
@RequestMapping("/friendship")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @Operation(summary = "Request to be friend",
            description = "You should choose person, you want to be friend with.")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @PostMapping("/user/{userId}/friend/{friendId}")
    public void askFriendship(@PathVariable("userId") Integer userId,
                              @PathVariable("friendId") Integer friendId) {
        friendshipService.askFriendship(userId, friendId);
    }

    @Operation(summary = "Friendship response",
            description = "If the person agreed, you become friends. If person refused, you become his subscriber.")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @PatchMapping("/{askId}/{ansId}/{flag}")
    public void answerFriendship(@PathVariable("askId") Integer askId,
                                 @PathVariable("ansId") Integer ansId,
                                 @PathVariable("flag") Boolean flag) {
        friendshipService.answerFriendship(askId, ansId, flag);
    }

    @Operation(summary = "Friendship delete",
            description = "If you want to end friendship with someone, you must make this request.")
    @ApiResponse(responseCode = "200", description = "Friendship was deleted ")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @DeleteMapping("/{friendshipId1}/{friendshipId2}")
    public void deleteFriendship(@Parameter(description = "The id user's friendship, who want to end friendship", required = true)
                                 @PathVariable("friendshipId1") Integer friendshipId1,
                                 @Parameter(description = "The user's id of friendship, who will lose friend, " +
                                         "but save subscription for former friend", required = true)
                                 @PathVariable("friendshipId2") Integer friendshipId2) {
        friendshipService.deleteFriendship(friendshipId1, friendshipId2);
    }

    @Operation(summary = "Delete subscription",
            description = "If you want to unsubscribe from someone, make this request.")
    @ApiResponse(responseCode = "200", description = "Subscription was deleted ")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @DeleteMapping("subscription/{friendshipId}")
    public void deleteSubscription(@PathVariable("friendshipId") Integer friendshipId) {
        friendshipService.deleteSubscription(friendshipId);
    }

}
