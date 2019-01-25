package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Pins;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PinRepository extends JpaRepository<Pins,Integer> {
    List<Pins>getAllByTopicId(Integer id);
}
