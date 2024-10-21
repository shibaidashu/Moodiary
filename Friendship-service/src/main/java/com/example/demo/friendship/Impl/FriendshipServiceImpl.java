package com.example.demo.friendship.Impl;

import com.example.demo.common.DTO.UserDTO;
import com.example.demo.common.Entity.Friendship;
import com.example.demo.common.Entity.User;
import com.example.demo.friendship.Mapper.FriendshipMapper;
import com.example.demo.friendship.Service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {
    @Autowired
    FriendshipMapper friendshipMapper;

    @Override
    public User getUserByEmail(String email) {
        return friendshipMapper.getUserByEmail(email);
    }

    @Override
    public void buildFriendship(int userId1, int userId2) {
        friendshipMapper.BuildRelationship(userId1,userId2);
    }

    @Override
    public Friendship getFriendship(int friendshipId) {
        return friendshipMapper.GetFriendship(friendshipId);
    }

    @Override
    public Friendship getFriendshipByTwoUserId(int userId1, int userId2) {
        return friendshipMapper.GetFriendshipByTwoUserId(userId1,userId2);
    }

    @Override
    public List<UserDTO> getFollowingList(Integer userId) {
        // 从数据库中获取该用户关注的用户列表
        return friendshipMapper.getFollowingListByUserId(userId);
    }


}
