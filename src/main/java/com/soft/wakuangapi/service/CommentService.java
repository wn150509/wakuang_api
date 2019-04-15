package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.ArticleComment;
import com.soft.wakuangapi.utils.ResponseUtil;

public interface CommentService {
    ResponseUtil addComment(ArticleComment articleComment);

    ResponseUtil deleteComment(Integer id);

    ResponseUtil getComments(Integer id);
}
