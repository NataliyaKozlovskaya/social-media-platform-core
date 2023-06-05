package com.social.media.platform.core.controllers;

import com.social.media.platform.core.models.Post;
import com.social.media.platform.core.services.FriendshipService;
import com.social.media.platform.core.services.MessageService;
import com.social.media.platform.core.services.PostService;
import com.social.media.platform.core.services.UserService;
import com.social.media.platform.core.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {
    private final PostService postService;
    private final FriendshipService friendshipService;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public UserController(PostService postService, FriendshipService friendshipService, MessageService messageService, UserService userService) {
        this.postService = postService;
        this.friendshipService = friendshipService;
        this.messageService = messageService;
        this.userService = userService;
    }


    //--------------------------- Посты: создание, просмотр, редактирование, удаление -------------------------------------------------------

    @GetMapping("/allPost/{userId}")
    public List<Post> findPostByUserId(@PathVariable("userId") Integer userId) {
        return postService.findPostByUserId(userId);
    }

    @GetMapping("/postById/{id}")
    public List<Post> findAllById(@PathVariable("id") Integer id) {
        return postService.findAllById(id);
    }

    //-----------------------------------------------------
//    @PostMapping("/{userId}/newPost")
//    public ResponseEntity<ResponseMessage> createNewPost(@PathVariable("userId") Integer userId,
//                                                         @RequestBody Post newPost,
//                                                         @RequestParam("files") MultipartFile[] files) {
//        postService.createNewPost(userId, newPost);
//
//        String message = "";
//        try {
//            List<String> fileNames = new ArrayList<>();
//
//            Arrays.asList(files).stream().forEach(file -> {
//                postService.saveFile(file);
//                fileNames.add(file.getOriginalFilename());
//            });
//            message = "Uploaded the files successfully: " + fileNames;
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//        } catch (Exception e) {
//            message = "Fail to upload files!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//        }
//    }

//---------опытный метод ---------------
//    @PostMapping("/newPicture")
//    public ResponseEntity<ResponseMessage> createPicture(@RequestParam("files") MultipartFile[] files){
//
//        String message = "";
//        try {
//            List<String> fileNames = new ArrayList<>();
//
//            Arrays.asList(files).stream().forEach(file -> {
//                postService.saveFile(file);
//                fileNames.add(file.getOriginalFilename());
//            });
//            message = "Uploaded the files successfully: " + fileNames;
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//        } catch (Exception e) {
//            message = "Fail to upload files!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//        }
//    }

    @PostMapping("/newPicture")
    public void newPicture(@RequestParam("file") MultipartFile file) {
        postService.saveFile(file);
//
    }

        //--------------------------------------------

    @PatchMapping("/{id}/updatedPost")
    public void updatePost(@PathVariable("id") Integer id, @RequestBody Post updatedPost) {
        postService.updatePost(id, updatedPost);
    }


//----------------------------------------------------------------
    @DeleteMapping("/{id}")
    public void updatePost(@PathVariable("id") Integer id) {
        postService.delete(id);
    }

    //------------------------Дружба, подписка -----------------------------------------------------

    @PostMapping("/friendship/user/{userId}/friend/{friendId}")
    public void askFriendship(@PathVariable("userId") Integer userId,
                              @PathVariable("friendId") Integer friendId) {
        friendshipService.askFriendship(userId, friendId);
    }

    @PatchMapping("/friendship/{askId}/{ansId}/{flag}")
    public void answerFriendship(@PathVariable("askId") Integer askId,
                                 @PathVariable("ansId") Integer ansId,
                                 @PathVariable("flag") Boolean flag) {
        friendshipService.answerFriendship(askId, ansId, flag);
    }

    @DeleteMapping("friendship/{friendshipId1}/{friendshipId2}")
    public void deleteFriendship(@PathVariable("friendshipId1") Integer friendshipId1,
                                 @PathVariable("friendshipId2") Integer friendshipId2) {
        friendshipService.deleteFriendship(friendshipId1, friendshipId2);
    }

    @DeleteMapping("subscription/{friendshipId}")
    public void deleteSubscription(@PathVariable("friendshipId") Integer friendshipId) {
        friendshipService.deleteSubscription(friendshipId);
    }

//------------------------------- Запросы на переписку -------------------------------------------------------------

    @PostMapping("/correspondence/userFrom/{fromUserId}/userTo/{toUserId}")
    public void askCorrespondence(@PathVariable("fromUserId") Integer fromUserId,
                                  @PathVariable("toUserId") Integer toUserId) {
        friendshipService.askCorrespondence(fromUserId, toUserId);
    }

    @PatchMapping("/correspondence/{correspondenceId1}/{correspondenceId2}/{approval}")
    public void ansCorrespondence(@PathVariable("correspondenceId1") Integer correspondenceId1,
                                  @PathVariable("correspondenceId2") Integer correspondenceId2,
                                  @PathVariable("approval") Boolean approval) {
        friendshipService.ansCorrespondence(correspondenceId1, correspondenceId2, approval);
    }

}



//контроллер controller advice




