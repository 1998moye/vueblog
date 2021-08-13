package com.myblog.mapper;

import com.myblog.entity.MLike;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeMapper {
    public void updateLike(MLike mLike);
    public MLike getLike(Long userId, Long blogId);
    public void insertLike(MLike mLike);
}
