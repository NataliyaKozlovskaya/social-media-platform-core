package com.social.media.platform.core.controllers;

import com.social.media.platform.core.models.Post;
import com.social.media.platform.core.services.FriendshipService;
import com.social.media.platform.core.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/allPost/{userId}")
    public List<Post> findPostByUserId(@PathVariable("userId") Integer userId) {
        return postService.findPostByUserId(userId);
    }

    @GetMapping("/postById/{id}")
    public List<Post> findAllById(@PathVariable("id") Integer id) {
        return postService.findAllById(id);
    }

    @GetMapping("/activePost/{userId}/{page}/{size}")
    public Page<Post> findActivePosts(@PathVariable("userId") Integer userId,
                                      @PathVariable("page") Integer page,
                                      @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("time").descending());
        return postService.findAllPosts(userId, pageable);
    }

    @PostMapping("/{userId}/newPost")
    public void createNewPost(@PathVariable("userId") Integer userId,
                              @RequestPart("newPost") Post newPost,
                              @RequestParam("files") MultipartFile[] files) {
        postService.createNewPost(userId, newPost, files);
    }

    @PatchMapping("/{id}/updatedPost")
    public void updatePost(@PathVariable("id") Integer id, @RequestBody Post updatedPost) {
        postService.updatePost(id, updatedPost);
    }

    @DeleteMapping("/{id}")
    public void updatePost(@PathVariable("id") Integer id) {
        postService.delete(id);
    }

}