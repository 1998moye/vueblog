package com.test;

import com.myblog.entity.MLike;
import com.myblog.mapper.UserLikeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LikeTest {
    @Autowired
    UserLikeMapper userLikeMapper;

    @Test
    public void getLike(){
       MLike mLike = userLikeMapper.getLike(1l, 2l);
       if(mLike != null){
           int liked = mLike.getLiked() > 0 ? 0 : 1;
           mLike.setLiked(liked);
           userLikeMapper.updateLike(mLike);
       }else{
           MLike mLike1 = new MLike();
           mLike1.setLiked(1);
           mLike1.setUserId(1l);
           mLike1.setBlogId(2l);
           userLikeMapper.insertLike(mLike1);
       }
    }
}
