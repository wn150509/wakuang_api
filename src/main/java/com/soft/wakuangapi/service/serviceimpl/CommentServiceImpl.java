package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.*;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.CommentService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Collections;

@Service
public class CommentServiceImpl implements CommentService{
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private CommentlikeRepository commentlikeRepository;

    @Override
    public ResponseUtil addComment(ArticleComment articleComment) {
        ArticleComment articleComment1=new ArticleComment();
        articleComment1.setArticleId(articleComment.getArticleId());
        articleComment1.setCommentContent(articleComment.getCommentContent());
        articleComment1.setUserId(articleComment.getUserId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date=null;
        try {
            date=df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        articleComment1.setCommentTime(date);
        return new ResponseUtil(0,"insert one comment",commentRepository.save(articleComment1));
    }

    @Override
    public ResponseUtil deleteComment(ArticleComment articleComment) {
        return new ResponseUtil(0,"delete comment",commentRepository.deleteArticleCommentByCommentId(articleComment.getCommentId()));
    }

    @Override
    public ResponseUtil getComments(ArticleLike articleLike) {
        List<CommentStatus>commentStatuses=getCommentStatus(articleLike);
        return new ResponseUtil(0,"get comments status",paixuTime(commentStatuses));
    }

    @Override
    public ResponseUtil insertCommentLike(CommentLike commentLike) {
        return new ResponseUtil(0,"insert like comment",commentlikeRepository.save(commentLike)) ;
    }

    @Override
    public ResponseUtil deleteCommentLike(CommentLike commentLike) {
        return new ResponseUtil(0,"delete like comment",commentlikeRepository.deleteCommentLikeByCommentIdAndUserId(commentLike.getCommentId(),commentLike.getUserId()));
    }

    public List<CommentStatus>getCommentStatus(ArticleLike articleLike){
        List<ArticleComment>articleCommentList=commentRepository.findAllByArticleId(articleLike.getArticleId());//找到谋篇文章下的所有评论
        List<CommentLike>commentLikes=commentlikeRepository.findAllByUserId(articleLike.getUserId());//找到该用户喜欢的所有评论
        List<CommentStatus>commentStatuses=new ArrayList<>();
        for (int i=0;i<articleCommentList.size();i++){
            ArticleComment articleComment=articleCommentList.get(i);
            int status=0;
            List<CommentLike>commentLikeList=commentlikeRepository.findAllByCommentId(articleComment.getCommentId());
            for (int j=0;j<commentLikes.size();j++){
                if (articleComment.getCommentId().equals(commentLikes.get(j).getCommentId())){
                    status=1;
                }
            }
            SysUser sysUser=sysUserRepository.findSysUserByUserId(articleComment.getUserId());
            CommentStatus commentStatus=new CommentStatus(articleComment.getCommentId(),articleComment.getArticleId(),sysUser,
                    articleComment.getCommentContent(),articleComment.getCommentTime(),commentLikeList.size(),status);
            commentStatuses.add(commentStatus);
        }
        return commentStatuses;
    }

    public List<CommentStatus> paixuTime(List<CommentStatus>commentStatuses){

        Collections.sort(commentStatuses, new Comparator<CommentStatus>(){
            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            public int compare(CommentStatus o1, CommentStatus o2) {
                //进行降序排列
                //将date类型的转换为String类型，然后截取和库中相同的长度，再转换为int；这样的截取方式已比对过，无差异
                Integer dateValue1 = Integer.valueOf(String.valueOf(o1.getCommentTime().getTime()).substring(0, 10));
                Integer dataValue2 = Integer.valueOf(String.valueOf(o2.getCommentTime().getTime()).substring(0,10));
                if(dateValue1 < dataValue2){
                    return 1;
                }
                if(dateValue1 == dataValue2){
                    return 0;
                }
                return -1;
            }
        });

        return commentStatuses;
    }

}
