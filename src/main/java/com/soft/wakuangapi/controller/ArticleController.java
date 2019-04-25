package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.ArticleService;
import com.soft.wakuangapi.service.CommentService;
import com.soft.wakuangapi.service.LikeService;
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
    @Resource
    private LikeService likeService;

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
    @RequestMapping(value = "/typeid",method = RequestMethod.POST)
    public ResponseUtil getTypeArticles(@RequestBody UserType userType){
        return new ResponseUtil(0,"get type",articleService.findTypearticle(userType));
    }
    @RequestMapping(value = "/typeid/time",method = RequestMethod.POST)
    public ResponseUtil getTypetime(@RequestBody UserType userType){
        return new ResponseUtil(0,"get type",articleService.findbytime(userType));
    }
    @RequestMapping(value = "/typeid/comment",method = RequestMethod.POST)
    public ResponseUtil getTypecomment(@RequestBody UserType userType){
        return new ResponseUtil(0,"get type",articleService.findbycomment(userType));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseUtil addNewArticle(@RequestBody Articles article){
        return articleService.releaseArticle(article);
    }

    @RequestMapping(value = "/oneArticle",method = RequestMethod.POST)
    public ResponseUtil getoneArticle(@RequestBody  ArticleLike articleLike){
        return new ResponseUtil(0,"get type",articleService.getoneArticle(articleLike));
    }

//    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
//    public ResponseUtil getArticleComment(@PathVariable Integer id){
//        return new ResponseUtil(0,"get type",articleService.getoneArticle(id));
//    }

    @RequestMapping(value = "/addcomment",method = RequestMethod.POST)
    public ResponseUtil addComment(@RequestBody ArticleComment articleComment){
        return commentService.addComment(articleComment);
    }

    @RequestMapping(value = "/delcomment",method = RequestMethod.POST)
    public ResponseUtil deleteComment(@RequestBody Integer id){
        return commentService.deleteComment(id);
    }

    @RequestMapping(value = "/queryarticle",method = RequestMethod.POST)
    public ResponseUtil queryarticle(@RequestBody SearchUser searchUser){
        return new ResponseUtil(0,"query article",articleService.queryArticle(searchUser));
    }

    @RequestMapping(value = "/selfArticlesbytime",method = RequestMethod.POST)
    public ResponseUtil getSelfArticlestime(@RequestBody SysUser sysUser){
        return articleService.getSomeOneArticlesbytime(sysUser.getUserId());
    }

    @RequestMapping(value = "/selfArticlesbylike",method = RequestMethod.POST)
    public ResponseUtil getSelfArticleslike(@RequestBody SysUser sysUser){
        return articleService.getSomeOneArticlesbylike(sysUser.getUserId());
    }

    @RequestMapping(value = "/insertlike",method = RequestMethod.POST)
    public ResponseUtil insertLikeArticle(@RequestBody ArticleLike articleLike){
        return likeService.insertLikeArticle(articleLike);
    }

    @RequestMapping(value = "/deletelike",method = RequestMethod.POST)
    public ResponseUtil deleteLikeArticle(@RequestBody ArticleLike articleLike){
        return likeService.deleteLikeArticle(articleLike);
    }

    @RequestMapping(value = "/articlestatus",method = RequestMethod.POST)
    public ResponseUtil getAllArticleStatus(@RequestBody SysUser sysUser){
        return new ResponseUtil(0,"get all article status",likeService.getArticleStatus(sysUser.getUserId()));
    }

    @RequestMapping(value = "/selflikearticles",method = RequestMethod.POST)
    public ResponseUtil getSomeOneLikeArticles(@RequestBody SysUser sysUser){
        return articleService.getSomeOneLikeArticles(sysUser.getUserId());
    }

    @RequestMapping(value = "/otherarticlesbytime",method = RequestMethod.POST)
    public ResponseUtil getOtherArticlesByTime(@RequestBody UserUser userUser){
        return articleService.getOtherArticleByTime(userUser);
    }

    @RequestMapping(value = "/otherarticlesbylike",method = RequestMethod.POST)
    public ResponseUtil getOtherArticlesByLike(@RequestBody UserUser userUser){
        return articleService.getOtherArticleByLike(userUser);
    }

    @RequestMapping(value = "/otherlikearticles",method = RequestMethod.POST)
    public ResponseUtil getOtherLikeArticles(@RequestBody UserUser userUser){
        return articleService.getOtherLikeArticles(userUser);
    }

    @RequestMapping(value = "/getRelativeArticles",method = RequestMethod.POST)
    public ResponseUtil getRelativeArticles(@RequestBody ArticleLike articleLike){
        return articleService.getRelativeArticles(articleLike);
    }
}
