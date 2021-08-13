package com.myblog.service.impl;

import com.myblog.entity.MUser;
import com.myblog.mapper.MUserMapper;
import com.myblog.service.IMUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class MUserServiceImpl extends ServiceImpl<MUserMapper, MUser> implements IMUserService {

}
