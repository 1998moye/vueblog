package com.myblog.service;

import com.myblog.entity.MBlog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-07-08
 */
public interface IMBlogService extends IService<MBlog> {
    public void updateLikes(int liked, Long blogId);
}
