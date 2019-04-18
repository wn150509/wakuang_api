package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.UserUser;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserConcernRepository extends JpaRepository<UserUser,Integer> {
    List<UserUser>findAllByUserId(Integer userId);

    List<UserUser>findAllByConcerneduserId(Integer concerneduserId);

    @Transactional
    int deleteUserUserByUserIdAndConcerneduserId(Integer userId,Integer concerneduserId);

    UserUser findUserUserByConcerneduserIdAndUserId(Integer concerneduserId,Integer userId);
}
