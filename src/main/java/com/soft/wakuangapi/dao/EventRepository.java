package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Events,Integer> {
    List<Events>findAll();
}
