package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.PinUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PinconcernRepository extends JpaRepository<PinUser,Integer> {
    @Transactional
    int deletePinUserByPinIdAndUserId(Integer pinId,Integer userId);

    @Transactional
    int deleteAllByPinId(Integer pinId);

    List<PinUser>findPinUsersByUserId(Integer userId);

    List<PinUser>findPinUsersByPinId(Integer pinId);
}
