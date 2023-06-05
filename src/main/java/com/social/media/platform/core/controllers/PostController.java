package com.social.media.platform.core.controllers;

import com.social.media.platform.core.models.Post;
import com.social.media.platform.core.services.FriendshipService;
import com.social.media.platform.core.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private final FriendshipService friendshipService;

    public PostController(PostService postService, FriendshipService friendshipService) {
        this.postService = postService;
        this.friendshipService = friendshipService;
    }

    @GetMapping("/showActivePost/{userId}")
    public List<Post> findActivePost(@PathVariable("userId") Integer userId){
       return postService.findPost(userId);
    }

    @GetMapping("/showActivePostPagination/{userId}/{page}/{size}")
    public Page<Post> findActivePost(@PathVariable("userId") Integer userId,
                                     @PathVariable("page") Integer page,
                                     @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("time").descending());
        return postService.findAllPosts(userId, pageable);
    }
}