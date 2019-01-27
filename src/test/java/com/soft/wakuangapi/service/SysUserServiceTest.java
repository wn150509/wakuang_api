package com.soft.wakuangapi.service;

import com.soft.wakuangapi.dao.ConcernRepository;
import com.soft.wakuangapi.dao.SysUserRepository;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceTest {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private ArticleService articleService;
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
    public void getUser() throws Exception {
        List<Articles>articlesList=articleService.getFollowTime(1);
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //创建要显示的日期格式
//        //注意了，这里的   MM 在java中代表月份，而  mm 代表分钟， HH 代表24小时制的时间， hh 代表12小时制的时间,很严格的
//
//        Date date = articlesList.get(1).getCreateTime();      //将从数据库读出来的 timestamp 类型的时间转换为java的Date类型
//        String s = fmt.format(date);
        System.out.println(articlesList);
    }

}