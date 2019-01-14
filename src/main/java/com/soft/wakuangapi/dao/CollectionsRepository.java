package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Collections;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionsRepository extends JpaRepository<Collections,Integer> {
    List<Collections>findAll();

    List<Collections>findCollectionsByTypeId(Integer id);
}
