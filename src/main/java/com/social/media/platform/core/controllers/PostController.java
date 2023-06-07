package com.social.media.platform.core.controllers;

import com.social.media.platform.core.models.Post;
import com.social.media.platform.core.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Post controller", description = "Allows to create, update, delete, look through posts")
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Operation(summary = "Find post by user's id", description = "You can find any post, if you know user's id.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Post was found",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "Post was not found")})
    @GetMapping("/allPost/{userId}")
    public List<Post> findPostByUserId (@Parameter(description = "The user's id, whose posts are to be found", required = true)
                                       @PathVariable("userId") Integer userId) {
        return postService.findPostByUserId(userId);
    }

    @Operation(summary = "Find post by its id", description = "You can find any post, if you know its id.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Post was found",
                         content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    @GetMapping("/postById/{id}")
    public List<Post> findAllById(@PathVariable("id") Integer id) {
        return postService.findAllById(id);
    }


    @Operation(summary = "Look though posts", description = "You can look though posts of person you follow.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Post was found",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping("/activePost/{userId}/{page}/{size}")
    public Page<Post> findActivePosts(@PathVariable("userId") Integer userId,
                                      @PathVariable("page") Integer page,
                                      @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("time").descending());
        return postService.findAllPosts(userId, pageable);
    }

    @Operation(summary = "Create a new post", description = "You can create a new post and add an image to it.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Post was created"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    @PostMapping("/{userId}/newPost")
    public void createNewPost(@PathVariable("userId") Integer userId,
                              @RequestPart("newPost") Post newPost,
                              @RequestParam("files") MultipartFile[] files) {
        postService.createNewPost(userId, newPost, files);
    }


    @Operation(summary = "Update your post", description = "You can update any of your posts.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Post was updated"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @PatchMapping("/{id}/updatedPost")
    public void updatePost(@PathVariable("id") Integer id, @RequestBody Post updatedPost) {
        postService.updatePost(id, updatedPost);
    }


    @Operation(summary = "Delete post", description = "You can delete any of your posts using the post id.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Post was deleted"),
            @ApiResponse(responseCode = "404", description = "Post not found")})
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Integer id) {
        postService.delete(id);
    }

}