package com.social.media.platform.core.repositories;

import com.social.media.platform.core.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findPostByUserId(Integer userId);
    Page<Post> findAllPostsByUserIdIn(List<Integer> userIds, Pageable pageable);

}
