package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.CommentChildRepository;
import com.soft.wakuangapi.dao.CommentRepository;
import com.soft.wakuangapi.dao.SysUserRepository;
import com.soft.wakuangapi.entity.ArticleComment;
import com.soft.wakuangapi.entity.CommentChild;
import com.soft.wakuangapi.service.CommentService;
import com.soft.wakuangapi.utils.ResponseUtil;
import com.soft.wakuangapi.vo.CommentChildVo;
import com.soft.wakuangapi.vo.CommentVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private CommentChildRepository commentChildRepository;

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
    public ResponseUtil deleteComment(Integer id) {
        return new ResponseUtil(0,"delete comment",commentRepository.deleteArticleCommentByCommentId(id));
    }

    @Override
    public ResponseUtil getComments(Integer id) {
        List<CommentVo>commentVoList=new ArrayList<>();
        List<ArticleComment>articleCommentList=commentRepository.findArticleCommentsByArticleId(id);
        for(int i=0;i<articleCommentList.size();i++){
            CommentVo commentVo=new CommentVo();
            commentVo.setContent(articleCommentList.get(i).getCommentContent());
            commentVo.setTime(articleCommentList.get(i).getCommentTime());
            commentVo.setSysUser(sysUserRepository.findSysUserByUserId(articleCommentList.get(i).getUserId()));
            if(commentChildRepository.findCommentChildrenByUserId(articleCommentList.get(i).getUserId()).size()==0){
                commentVo.setCommentChildVoList(null);
            }else {
                List<CommentChildVo>commentChildVoList=new ArrayList<>();
                List<CommentChild>commentChildren=commentChildRepository.findCommentChildrenByUserId(articleCommentList.get(i).getUserId());
                for(int j=0;j<commentChildren.size();j++){

                }
            }
        }
        return null;
    }
}
