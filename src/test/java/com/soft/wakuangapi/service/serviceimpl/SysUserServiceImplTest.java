package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.SysUserRepository;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.entity.TopicUser;
import com.soft.wakuangapi.service.SysUserService;
import com.soft.wakuangapi.service.TopicUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserServiceImplTest {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private TopicUserService topicUserService;

    @Test
    public void updateUser() throws Exception {
        TopicUser topicUser=new TopicUser();
        topicUser.setTopicId(1);
        topicUser.setUserId(1);
        System.out.println(topicUserService.getTopicUser(1));
    }

}