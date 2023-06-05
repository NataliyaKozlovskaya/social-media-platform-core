package com.social.media.platform.core.services;

import com.social.media.platform.core.models.Message;
import com.social.media.platform.core.models.User;
import com.social.media.platform.core.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social.media.platform.core.repositories.MessageRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

//    public List<Message> findAll(Integer fromUserId, Integer toUserId){
//        return messageRepository.findAllByFromUserIdAndToUserId(fromUserId, toUserId);
//    }



}
