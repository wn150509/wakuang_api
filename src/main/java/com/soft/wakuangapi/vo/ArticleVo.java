package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.ArticleStatus;
import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.entity.UserStatus;
import lombok.Data;

@Data
public class ArticleVo {
    private UserStatus userStatus;
    private ArticleStatus articleStatus;

    public ArticleVo() {
    }

    public ArticleVo(UserStatus userStatus, ArticleStatus articleStatus) {
        this.userStatus = userStatus;
        this.articleStatus = articleStatus;
    }
}
