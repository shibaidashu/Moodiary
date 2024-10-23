package com.example.demo.post.Controller;

import com.example.demo.common.Entity.PageBean;
import com.example.demo.common.Entity.Post;
import com.example.demo.common.Utils.Result;
import com.example.demo.common.Utils.ThreadLocalUtil;
import com.example.demo.post.Service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/post")
@Api(tags = "朋友圈相关接口")
public class PostController {

    @Autowired
    private PostService postService;

    //发布朋友圈内容
    @ApiOperation("发布朋友圈内容")
    @PostMapping("/release")
    public Result addPost(@RequestBody Post post) {
        log.info("发布朋友圈内容：{}", post);
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = Integer.parseInt(claims.get("id").toString());
        post.setUserId(userId);
        postService.addPost(post);
        return Result.success();
    }

    //分页查询朋友圈内容
    @ApiOperation("分页查询朋友圈内容")
    @GetMapping("/check")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        //记录日志
        log.info("分页查询，参数：{},{}", page, pageSize);
        //调用业务层分页查询功能
        PageBean pageBean = postService.page(page, pageSize);
        //响应
        return Result.success(pageBean);
    }
}


