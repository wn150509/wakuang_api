package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Labels;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabelsRepository extends JpaRepository<Labels,Integer>{

    List<Labels>findAll();

    Labels findLabelsByLabelsId(Integer id);

    Labels findLabelsByLabelsName(String name);

    /**
     *
     * @param labels_name 传入参数
     * @return
     */
    @Query(value = "select * from labels where labels.labels_name LIKE CONCAT('%',:labels_name,'%')", nativeQuery = true)
    List<Labels> queryLabelsList(@Param("labels_name") String labels_name);
}
