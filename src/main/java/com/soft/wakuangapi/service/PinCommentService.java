package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.PinComment;
import com.soft.wakuangapi.entity.PinCommentLike;
import com.soft.wakuangapi.entity.PinUser;
import com.soft.wakuangapi.utils.ResponseUtil;

public interface PinCommentService {
    ResponseUtil addPinComment(PinComment pinComment);

    ResponseUtil deltePinComment(PinComment pinComment);

    ResponseUtil getPinComments(PinUser pinUser);

    ResponseUtil insertPinCommentLike(PinCommentLike pinCommentLike);

    ResponseUtil deltePinCommentLike(PinCommentLike pinCommentLike);
}
