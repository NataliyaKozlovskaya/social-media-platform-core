package com.social.media.platform.core.services;

import com.social.media.platform.core.models.Picture;
import com.social.media.platform.core.models.Post;
import com.social.media.platform.core.models.User;
import com.social.media.platform.core.repositories.FriendshipRepository;
import com.social.media.platform.core.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social.media.platform.core.repositories.PostRepository;
import com.social.media.platform.core.repositories.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository,
                       FriendshipRepository friendshipRepository, PictureRepository pictureRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.pictureRepository = pictureRepository;
    }

    public List<Post> findAllById(Integer postId){
        return postRepository.findAllById(Collections.singleton(postId));
    }

    public List<Post> findPostByUserId(Integer userId){
        return postRepository.findPostByUserId(userId);
    }

    public Page<Post> findAllPosts (Integer userId, Pageable pageable){
        List<Integer> friendshipsId = friendshipRepository.listFriendshipId(userId);
        return postRepository.findAllPostsByUserIdIn(friendshipsId, pageable);
    }

    @Transactional
    public void createNewPost(Integer userId, Post newPost, MultipartFile[] files) {
        User user = userRepository.getReferenceById(userId);
        newPost.setTime(new Date());
        newPost.setUser(user);
        postRepository.save(newPost);

        user.getListPosts().add(newPost);

        Picture picture = new Picture();
        picture.setPost(newPost);
        Stream.of(files).forEach(file-> {
            try {
                byte[] bytes = file.getBytes();
                picture.setIcon(bytes);
                pictureRepository.save(picture);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

        @Transactional
        public void updatePost (Integer postId, Post updatedPost){
            updatedPost.setId(postId);
            updatedPost.setUpdatedTime(new Date());
            postRepository.save(updatedPost);
        }

        @Transactional
        public void delete (Integer id){
            postRepository.deleteById(id);
        }

    }



