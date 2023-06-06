package com.social.media.platform.core.services;

import com.social.media.platform.core.models.Friendship;
import com.social.media.platform.core.models.User;
import com.social.media.platform.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social.media.platform.core.repositories.FriendshipRepository;

@Service
@Transactional(readOnly = true)
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void askFriendship(Integer userId, Integer friendId){
        User user = userRepository.getReferenceById(userId);
        User friend = userRepository.getReferenceById(friendId);

        Friendship friendship = new Friendship();
        friendship.setUser(user);
        friendship.setFriend(friend);
        friendship.setSubscription(true);
        friendship.setFriendship(false);

        Friendship friendship2 = new Friendship();
        friendship2.setUser(friend);
        friendship2.setFriend(user);

        //Subscription and Friendship, correspondence == null

        friendshipRepository.save(friendship);
        friendshipRepository.save(friendship2);

        user.getListFriends().add(friendship);
        user.getListFriends().add(friendship2);

        userRepository.save(user);
        userRepository.save(friend);
    }

    @Transactional
    public void answerFriendship(Integer askId,Integer ansId, boolean flag){
        Friendship friendshipAsk = friendshipRepository.getReferenceById(askId);
        Friendship friendshipAns = friendshipRepository.getReferenceById(ansId);
        if (flag){
            friendshipAsk.setFriendship(true);
            friendshipAns.setFriendship(true);

            friendshipAns.setSubscription(true);

            friendshipAsk.setCorrespondence(true);
            friendshipAns.setCorrespondence(true);
        }else{
            friendshipAsk.setFriendship(false);
            friendshipRepository.delete(friendshipAns);
        }
    }

    @Transactional
    public void deleteFriendship(Integer friendshipId1, Integer friendshipId2){
       friendshipRepository.deleteById(friendshipId1);
       Friendship friendship = friendshipRepository.getReferenceById(friendshipId2);
       friendship.setFriendship(false);
       friendship.setCorrespondence(false);
    }

    @Transactional
    public void deleteSubscription(Integer friendshipId){
        friendshipRepository.deleteById(friendshipId);
    }

//  запрос на переписку в случае, если user'ы не друзья
    @Transactional
    public void askCorrespondence(Integer fromUserId, Integer toUserId) {
        User fromUser = userRepository.getReferenceById(fromUserId);
        User toUser = userRepository.getReferenceById(toUserId);

        Friendship friend = friendshipRepository.getReferenceById(toUserId);
        if(!fromUser.getListFriends().contains(friend)){
            Friendship friendship = new Friendship();
            friendship.setUser(fromUser);
            friendship.setFriend(toUser);
            friendshipRepository.save(friendship);

            Friendship friendship2 = new Friendship();
            friendship2.setUser(toUser);
            friendship2.setFriend(fromUser);
            friendshipRepository.save(friendship2);
        }
    }

    @Transactional
    public void ansCorrespondence(Integer correspondenceId1, Integer correspondenceId2,
                                  Boolean approval) {
        Friendship correspondence1 = friendshipRepository.getReferenceById(correspondenceId1);
        Friendship correspondence2 = friendshipRepository.getReferenceById(correspondenceId2);
        if(approval){
            correspondence1.setCorrespondence(true);
            correspondence2.setCorrespondence(true);
        }else{
            friendshipRepository.delete(correspondence1);
            friendshipRepository.delete(correspondence2);
        }
    }

}
