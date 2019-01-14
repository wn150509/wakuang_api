package com.soft.wakuangapi.service;

import com.soft.wakuangapi.dao.ConcernRepository;
import com.soft.wakuangapi.dao.SysUserRepository;
import com.soft.wakuangapi.entity.ConcernUser;
import com.soft.wakuangapi.entity.Labels;
import com.soft.wakuangapi.entity.LoginUser;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceTest {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private ConcernRepository concernService;
    @Resource
    private SysUserService sysUserService;
    @Test
    public void userLogin() throws Exception {
        SysUser sysUser = sysUserRepository.findSysUserByEmail("111");
        //邮箱有效
        if (sysUser != null) {
            //判断密码是否正确
            if ("111".equals(sysUser.getPassword())) {
                System.out.println(new ResponseUtil(0, "login success", sysUser));
            } else {
                System.out.println(new ResponseUtil(1, "password wrong"));
            }
        } else {
            System.out.println(new ResponseUtil(1, "account wrong"));
        }
    }

    @Test
    public void findalllabels() throws Exception {
        ConcernUser concernUser=new ConcernUser();
        concernUser.setLabelId(3);
        concernUser.setUserId(2);
        System.out.println(concernService.save(concernUser));
    }

}