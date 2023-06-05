package com.social.media.platform.core.repositories;

import com.social.media.platform.core.models.Post;
import com.social.media.platform.core.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    //List<Post> findAllById(Integer id);
    // List<Post> findAllByUserId(Integer id);

}
