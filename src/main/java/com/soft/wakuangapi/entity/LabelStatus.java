package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class LabelStatus {
    private Integer labelsId;
    private String labelsUrl;
    private String labelsName;
    private Integer articleCount;
    private Integer fansCount;
    private Integer status;

    public LabelStatus() {
    }

    public LabelStatus(Integer labelsId, String labelsUrl, String labelsName, Integer articleCount, Integer fansCount, Integer status) {
        this.labelsId = labelsId;
        this.labelsUrl = labelsUrl;
        this.labelsName = labelsName;
        this.articleCount = articleCount;
        this.fansCount = fansCount;
        this.status = status;
    }
}
