package com.soft.wakuangapi.service;


import com.soft.wakuangapi.entity.Labels;
import com.soft.wakuangapi.vo.LabelVo;

import java.util.List;

public interface LabelsService {
    List<Labels> findAllLabels();

    LabelVo getonelabel(Integer labelid,Integer userid);

    Labels getoneLabel(String name);
}
