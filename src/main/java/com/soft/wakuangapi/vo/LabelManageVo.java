package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.Labels;
import lombok.Data;

@Data
public class LabelManageVo {
    private Labels labels;
    private Integer concernCount;
    private Integer articleCount;

    public LabelManageVo() {
    }

    public LabelManageVo(Labels labels, Integer concernCount, Integer articleCount) {
        this.labels = labels;
        this.concernCount = concernCount;
        this.articleCount = articleCount;
    }
}
