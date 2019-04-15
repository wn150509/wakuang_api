package com.soft.wakuangapi.service;

import com.soft.wakuangapi.dao.ArticlesRepository;
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
    @Resource
    private CommentService commentService;
    @Resource
    private ArticlesRepository articlesRepository;
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
//        ArticleComment articleComment=new ArticleComment();
//        articleComment.setArticleId(1);
//        articleComment.setUserId(1);
//        articleComment.setCommentContent("写的好");
//        articleComment.setCommentTime(new Date());
//        System.out.println(commentService.addComment(articleComment));
//        System.out.println(commentService.deleteComment(33));
        List<Articles>articlesList=articlesRepository.queryAticleList("测试");
        System.out.println(articlesList.size());
    }

}