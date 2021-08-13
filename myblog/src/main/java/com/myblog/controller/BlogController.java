package com.myblog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myblog.entity.AccountProfile;
import com.myblog.entity.MBlog;
import com.myblog.entity.MLike;
import com.myblog.entity.MUser;
import com.myblog.res.Result;
import com.myblog.service.IMBlogService;
import com.myblog.service.IMUserService;
import com.myblog.service.UserLikeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BlogController {

    @Autowired
    IMUserService userService;

    @Autowired
    IMBlogService blogService;

    @Autowired
    UserLikeService userLikeService;

    @GetMapping("/blogs")
    public Result blogs(@RequestParam(required = false) long current, @RequestParam(required = false) long size){
        size = size == 0 ? 5 : size;
        Page<MBlog> page = new Page<>(current, size);
        Page<MBlog> blogs = blogService.page(page, new QueryWrapper<MBlog>().orderByDesc("created").select("id", "user_id", "title"
        , "description", "created", "status", "status", "cover", "scans", "likes"));
//        System.out.println(blogs);
        return Result.success(null,blogs);
    }


    @GetMapping("/detail/{id}")
    public Result catBlog(@PathVariable long id){
        MBlog blog = blogService.getById(id);
        blog.setScans(blog.getScans() + 1);
        blogService.update(blog, new UpdateWrapper<MBlog>().eq("id", blog.getId()));
        MUser user = userService.getById(blog.getUserId());
        Assert.notNull(blog, "博客不存在");
        return Result.success(user.getUsername(), blog);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result editBlog(@RequestBody MBlog blog){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        AccountProfile accountProfile = (AccountProfile)principal;
        System.out.println(accountProfile.getId());
        MBlog temp = null;
        if(blog.getId() == null){
            temp = new MBlog();
            temp.setStatus(0);
            temp.setCreated(LocalDateTime.now());
            temp.setUserId(accountProfile.getId());
        }else{
            temp = blogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId().equals(accountProfile.getId()), "没有权限编辑");

        }
        System.out.println(blog.getId());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        System.out.println(temp);
        blogService.saveOrUpdate(temp);
        return Result.success("编辑成功", null);
    }

    @RequiresAuthentication
    @GetMapping("/like")
    public Result giveLike(long userId, long blogId){
        MBlog blog = blogService.getById(blogId);
        if(blog != null){
            MLike mLike = new MLike();
            mLike.setBlogId(blogId);
            mLike.setUserId(userId);
            userLikeService.saveOrUpdateLike(mLike);
            MLike like = userLikeService.getLike(mLike);
            return Result.success("添加成功", like.getLiked());
        }else{
            return Result.fail("博客不存在", false);
        }
    }

    @RequiresAuthentication
    @GetMapping("/getLike")
    public Result getLike(long userId, long blogId){
        System.out.println("执行");
        MLike mLike = new MLike();
        mLike.setUserId(userId);
        mLike.setBlogId(blogId);
        MLike like = userLikeService.getLike(mLike);
        if(like != null && like.getLiked() > 0){
            return Result.success(null, 1);
        }
        return Result.success(null, 0);
    }
}
