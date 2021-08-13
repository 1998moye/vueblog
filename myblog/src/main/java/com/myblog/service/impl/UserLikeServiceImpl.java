package com.myblog.service.impl;

import com.myblog.entity.MLike;
import com.myblog.mapper.MBlogMapper;
import com.myblog.mapper.UserLikeMapper;
import com.myblog.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserLikeServiceImpl implements UserLikeService {

    @Autowired
    UserLikeMapper userLikeMapper;

    @Autowired
    MBlogMapper blogMapper;

    @Override
    public void saveOrUpdateLike(MLike mLike) {
        MLike resLike = userLikeMapper.getLike(mLike.getUserId(), mLike.getBlogId());
        if(resLike != null){
            int liked = resLike.getLiked() > 0 ? 0 : 1;
            resLike.setLiked(liked);
            userLikeMapper.updateLike(resLike);
            int likes = liked > 0 ? 1 : -1;
            blogMapper.updateLikes(likes, mLike.getBlogId());
        }else{
            MLike mLike1 = new MLike();
            mLike1.setLiked(1);
            mLike1.setUserId(mLike.getUserId());
            mLike1.setBlogId(mLike.getBlogId());
            userLikeMapper.insertLike(mLike1);
            blogMapper.updateLikes(1, mLike.getBlogId());
        }
    }

    @Override
    public MLike getLike(MLike mLike) {
        MLike like = userLikeMapper.getLike(mLike.getUserId(), mLike.getBlogId());
        return like;
    }
}
