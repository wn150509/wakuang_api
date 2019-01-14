package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.entity.LabelStatus;
import lombok.Data;

import java.util.List;

@Data
public class LabelVo {
    private LabelStatus labelStatus;
    private List<Articles>articlesList;

    public LabelVo() {
    }

    public LabelVo(LabelStatus labelStatus, List<Articles> articlesList) {
        this.labelStatus = labelStatus;
        this.articlesList = articlesList;
    }
}
