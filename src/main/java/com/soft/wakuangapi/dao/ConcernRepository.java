package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.ConcernUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ConcernRepository extends JpaRepository<ConcernUser,Integer> {
    List<ConcernUser>findAllByUserId(Integer id);

    @Transactional
    int deleteConcernUserByUserIdAndLabelId(Integer userid,Integer labelid);

    @Transactional
    int deleteAllByLabelId(Integer labelId);

    List<ConcernUser>findAllByLabelId(Integer id);

    ConcernUser findConcernUserByLabelIdAndUserId(Integer labelid,Integer userid);
}
