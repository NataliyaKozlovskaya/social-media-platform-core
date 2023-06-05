package com.social.media.platform.core.repositories;

import com.social.media.platform.core.models.Friendship;
import com.social.media.platform.core.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    @Query(value = "SELECT friend_id FROM Friendship WHERE user_id=?1 and subscription = true", nativeQuery = true)
    List<Integer> listFriendship(Integer userId);


}
