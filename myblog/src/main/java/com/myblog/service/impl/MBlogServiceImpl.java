package com.myblog.service.impl;

import com.myblog.entity.MBlog;
import com.myblog.mapper.MBlogMapper;
import com.myblog.service.IMBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-07-08
 */
@Service
@Transactional
public class MBlogServiceImpl extends ServiceImpl<MBlogMapper, MBlog> implements IMBlogService {

    @Autowired
    MBlogMapper blogMapper;

    @Override
    public void updateLikes(int liked, Long blogId) {
        blogMapper.updateLikes(liked, blogId);
    }
}
