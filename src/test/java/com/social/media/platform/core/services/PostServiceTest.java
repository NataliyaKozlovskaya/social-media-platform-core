package com.social.media.platform.core.services;

import com.social.media.platform.core.models.Picture;
import com.social.media.platform.core.models.Post;
import com.social.media.platform.core.models.User;
import com.social.media.platform.core.repositories.FriendshipRepository;
import com.social.media.platform.core.repositories.PictureRepository;
import com.social.media.platform.core.repositories.PostRepository;
import com.social.media.platform.core.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private FriendshipRepository friendshipRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PictureRepository pictureRepository;


    @Test
    void shouldFindPostById() {
        postService.findPostById(1);
        Mockito.verify(postRepository, times(1)).findById(1);
    }


    @Test
    void shouldFindPostByUserId() {
        List<Post> listPosts = getAllPosts();

        Mockito.when(postRepository.findPostByUserId(1)).thenReturn(listPosts);

        Assertions.assertEquals(2, listPosts.size());

        postService.findPostByUserId(1);
        Mockito.verify(postRepository, times(1)).findPostByUserId(1);
    }


    @Test
    void shouldFindAllPostsBySubscriptions() {
        Pageable pageable = PageRequest.of(1, 2, Sort.by("time").descending());
        List<Integer> listFriendId = List.of(2);

        Page<Post> listPosts = new PageImpl<>(getAllPosts());

        Mockito.when(friendshipRepository.listFriendshipId(1)).thenReturn(listFriendId);
        Mockito.when(postRepository.findAllPostsByUserIdIn(listFriendId, pageable)).thenReturn(listPosts);

        Assertions.assertNotNull(listPosts);
        Assertions.assertEquals(2, listPosts.getSize());
        postService.findAllPosts(1, pageable);
    }


    @Test
    void shouldCreateNewPost() {
        User user = new User();
        Post post = new Post();

        MultipartFile[] files = new MultipartFile[2];
        MultipartFile multipartFile = new MockMultipartFile("File1", "Hello, World".getBytes());
        MultipartFile multipartFile2 = new MockMultipartFile("File2", "Good by, World".getBytes());
        files[0] = multipartFile;
        files[1] = multipartFile2;

        Mockito.when(userRepository.getReferenceById(1)).thenReturn(user);

        Mockito.when(postRepository.save(post)).thenReturn(post);

        user.getListPosts().add(post);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        Stream.of(files).forEach(file-> {
            try {
                Picture picture= new Picture();
                byte[] bytes1 = file.getBytes();
                picture.setIcon(bytes1);
                picture.setPost(post);
                Mockito.when(pictureRepository.save(picture)).thenReturn(picture);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        postService.createNewPost(1, post, files);
    }


    @Test
    void updatePost() {
        Post newPost = new Post();
        postService.updatePost(newPost);
        Mockito.verify(postRepository, times(1)).save(newPost);
    }

    @Test
    void delete() {
       postService.delete(1);
       Mockito.verify(postRepository, times(1)).deleteById(1);
    }

    private List<Post> getAllPosts(){
        Post post1 = new Post();
        Post post2 = new Post();
        return List.of(post1, post2);
    }
}