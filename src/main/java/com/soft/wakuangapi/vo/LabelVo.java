package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.ArticleStatus;
import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.entity.LabelStatus;
import lombok.Data;

import java.util.List;

@Data
public class LabelVo {
    private LabelStatus labelStatus;
    private List<ArticleStatus>articleStatusList;

    public LabelVo() {
    }

    public LabelVo(LabelStatus labelStatus, List<ArticleStatus> articleStatusList) {
        this.labelStatus = labelStatus;
        this.articleStatusList = articleStatusList;
    }
}
