package com.social.media.platform.core.repositories;

import com.social.media.platform.core.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    //List <Message> findAllByFromUserIdAndToUserId(Integer fromUserId, Integer toUserId);
}
