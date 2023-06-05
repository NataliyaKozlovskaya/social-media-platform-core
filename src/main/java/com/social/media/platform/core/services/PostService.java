package com.social.media.platform.core.services;

import com.social.media.platform.core.models.Picture;
import com.social.media.platform.core.models.Post;
import com.social.media.platform.core.models.User;
import com.social.media.platform.core.repositories.FriendshipRepository;
import com.social.media.platform.core.repositories.PictureRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social.media.platform.core.repositories.PostRepository;
import com.social.media.platform.core.repositories.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class PostService {
    private static String UPLOADED_FOLDER = "C://temp//";
    private final Path root = Paths.get("uploads");
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final PictureRepository pictureRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, FriendshipRepository friendshipRepository, PictureRepository pictureRepository) {
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


//    @Transactional/// нужно ли сохранять в юзере посты?_____________________________
//    public void createNewPost(int userId, Post newPost) {
//        User user = userRepository.getReferenceById(userId);
//        newPost.setTime(new Date());
//        newPost.setUser(user);
//
//        Picture picture = new Picture();
//        picture.setPost(newPost);
//        pictureRepository.save(picture);
//    }

//    @Transactional
//    public void saveFile(MultipartFile file) {
//        try {
//            //Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
//            Files.copy(file.getInputStream(), pictureRepository.save(file.getOriginalFilename()));
//        } catch (Exception e) {
//            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
//        }
//    }
    @Transactional
    public void saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream("image"));
                       // new File("C:\\Users\\Наталия\\OneDrive\\Рабочий стол\\temp")));

                stream.write(bytes);
                stream.close();

            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        pictureRepository.save(file);
    }

//----------------------------------------------------------------------
        @Transactional// как проверить id пользователя, где его принять
        public void updatePost (Integer postId, Post updatedPost){
            updatedPost.setId(postId);
            updatedPost.setUpdatedTime(new Date());
            postRepository.save(updatedPost);
        }
        @Transactional
        public void delete ( int id){
            postRepository.deleteById(id);
        }


//-------------------------- Подписки и лента активности ----------------------------------------------------
        public List<Post> findPost (Integer userId){
            List<Integer> friendshipsId = friendshipRepository.listFriendship(userId);
            return postRepository.findPostsByUserIdIn(friendshipsId);
        }

        public Page<Post> findAllPosts (Integer userId, Pageable pageable){
            List<Integer> friendshipsId = friendshipRepository.listFriendship(userId);
            return postRepository.findAllPostsByUserIdIn(friendshipsId, pageable);
        }

    }



