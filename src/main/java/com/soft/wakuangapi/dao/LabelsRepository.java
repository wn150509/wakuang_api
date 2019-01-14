package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Labels;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelsRepository extends JpaRepository<Labels,Integer>{

    List<Labels>findAll();

    Labels findLabelsByLabelsId(Integer id);
}
