package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.Collections;

import java.util.List;

public interface CollectionsService {
    List<Collections>findallcollections();

    List<Collections>findcollectionsbytypeid(Integer id);
}
