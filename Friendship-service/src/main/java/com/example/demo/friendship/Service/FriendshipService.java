package com.example.demo.friendship.Service;

import com.example.demo.common.DTO.UserDTO;
import com.example.demo.common.Entity.Friendship;
import com.example.demo.common.Entity.User;

import java.util.List;

public interface FriendshipService {
    public User getUserByEmail(String email);

    public void buildFriendship(int userId1, int userId2);

    public Friendship getFriendship(int friendshipId);

    public Friendship getFriendshipByTwoUserId(int userId1, int userId2);

    public List<UserDTO> getFollowingList(Integer userId1);
}
