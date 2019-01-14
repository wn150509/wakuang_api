package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.CollectionsRepository;
import com.soft.wakuangapi.entity.Collections;
import com.soft.wakuangapi.service.CollectionsService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollectionsServiceImpl implements CollectionsService {
    @Resource
    private CollectionsRepository collectionsRepository;

    @Override
    public List<Collections> findallcollections() {
        return collectionsRepository.findAll();
    }

    @Override
    public List<Collections> findcollectionsbytypeid(Integer id) {
        return collectionsRepository.findCollectionsByTypeId(id);
    }
}
