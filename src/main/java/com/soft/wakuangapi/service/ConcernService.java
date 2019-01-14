package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.ConcernUser;
import com.soft.wakuangapi.entity.LabelStatus;
import com.soft.wakuangapi.utils.ResponseUtil;

import java.util.List;

public interface ConcernService {
    List<LabelStatus>getConcernLabels(Integer id);

    ResponseUtil insertConcern(ConcernUser concernUser);

    ResponseUtil deleteConcern(ConcernUser concernUser);
}
