package com.myblog.service;

import com.myblog.entity.MLike;

public interface UserLikeService {
    public void saveOrUpdateLike(MLike mLike);
    public MLike getLike(MLike mLike);
}
