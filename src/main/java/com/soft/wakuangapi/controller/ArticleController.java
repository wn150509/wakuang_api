package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.ArticleComment;
import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.service.ArticleService;
import com.soft.wakuangapi.service.CommentService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/articles")
@CrossOrigin("http://localhost:81")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private CommentService commentService;

    @RequestMapping(value = "/type/{id}",method = RequestMethod.GET)
    public ResponseUtil getTypeArticles(@PathVariable Integer id){
        return new ResponseUtil(0,"get type",articleService.findTypearticle(id));
    }
    @RequestMapping(value = "/type",method = RequestMethod.POST)
    public ResponseUtil getFollowArticles(@RequestBody SysUser sysUser){
        return new ResponseUtil(0,"get type",articleService.getFollowArticle(sysUser.getUserId()));
    }
    @RequestMapping(value = "/type/time",method = RequestMethod.POST)
    public ResponseUtil getFollowTime(@RequestBody SysUser sysUser){
        return new ResponseUtil(0,"get type",articleService.getFollowTime(sysUser.getUserId()));
    }
    @RequestMapping(value = "/type/comment",method = RequestMethod.POST)
    public ResponseUtil getFollowComment(@RequestBody SysUser sysUser){
        return new ResponseUtil(0,"get type",articleService.getFollowComment(sysUser.getUserId()));
    }
    @RequestMapping(value = "/type/{id}/time",method = RequestMethod.GET)
    public ResponseUtil getTypetime(@PathVariable Integer id){
        return new ResponseUtil(0,"get type",articleService.findbytime(id));
    }
    @RequestMapping(value = "/type/{id}/comment",method = RequestMethod.GET)
    public ResponseUtil getTypecomment(@PathVariable Integer id){
        return new ResponseUtil(0,"get type",articleService.findbycomment(id));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseUtil addNewArticle(@RequestBody Articles article){
        return articleService.releaseArticle(article);
    }

    @RequestMapping(value = "/article/{id}",method = RequestMethod.GET)
    public ResponseUtil getoneArticle(@PathVariable Integer id){
        return new ResponseUtil(0,"get type",articleService.getoneArticle(id));
    }

    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResponseUtil getArticleComment(@PathVariable Integer id){
        return new ResponseUtil(0,"get type",articleService.getoneArticle(id));
    }

    @RequestMapping(value = "/addcomment",method = RequestMethod.POST)
    public ResponseUtil addComment(@RequestBody ArticleComment articleComment){
        return commentService.addComment(articleComment);
    }

    @RequestMapping(value = "/delcomment",method = RequestMethod.POST)
    public ResponseUtil deleteComment(@RequestBody Integer id){
        return commentService.deleteComment(id);
    }

    @RequestMapping(value = "/queryarticle",method = RequestMethod.POST)
    public ResponseUtil queryarticle(@RequestBody Articles articles){
        return new ResponseUtil(0,"query article",articleService.queryArticle(articles.getArticleTitle()));
    }
}
