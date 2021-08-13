package com.myblog.mapper;

import com.myblog.entity.MBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-07-08
 */
@Repository
public interface MBlogMapper extends BaseMapper<MBlog> {
    public void updateLikes(int liked, Long blogId);
}
