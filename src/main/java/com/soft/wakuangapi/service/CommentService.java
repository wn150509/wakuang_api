package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.ArticleComment;
import com.soft.wakuangapi.entity.ArticleLike;
import com.soft.wakuangapi.entity.CommentLike;
import com.soft.wakuangapi.utils.ResponseUtil;

public interface CommentService {
    ResponseUtil addComment(ArticleComment articleComment);

    ResponseUtil deleteComment(ArticleComment articleComment);

    ResponseUtil getComments(ArticleLike articleLike);

    ResponseUtil insertCommentLike(CommentLike commentLike);

    ResponseUtil deleteCommentLike(CommentLike commentLike);

}
