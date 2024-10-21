package com.example.demo.friendship.Controller;

import com.example.demo.client.UserClient;
import com.example.demo.common.DTO.UserDTO;
import com.example.demo.common.Entity.User;
import com.example.demo.common.Utils.ThreadLocalUtil;
import com.example.demo.friendship.Service.FriendshipService;
import com.example.demo.common.Utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friendship")
@Api(tags = "好友关系相关接口")
public class FriendshipController {
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserClient userClient;

    private int getUserByEmail(String email) {
        User user = friendshipService.getUserByEmail(email);
        if (user != null) {
            return user.getUserId();
        }
        return -1;
    }

    @ApiOperation("增加好友关系")
    @PostMapping("/BuildRelations")
    public Result BuildRelations(@RequestParam String email){
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId1 = Integer.parseInt(claims.get("id").toString());
        Integer userId2 = getUserByEmail(email);
        if(userId1 != null && userClient.getUser(userId2) != null && userId1 != userId2){
            if(friendshipService.getFriendshipByTwoUserId(userId1, userId2)==null){
                friendshipService.buildFriendship(userId1,userId2);
                return Result.success();
            }
            return Result.error("已经是好友");
        }
        return Result.error("非法用户ID");
    }

    // 查看好友列表功能
    @ApiOperation("查看用户的好友列表")
    @GetMapping("/listFollowing")
    public Result listFollowing() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = Integer.parseInt(claims.get("id").toString());
        List<UserDTO> followingList = friendshipService.getFollowingList(userId);
        return Result.success(followingList);
    }

}
