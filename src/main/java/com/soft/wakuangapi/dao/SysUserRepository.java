package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysUserRepository extends JpaRepository<SysUser, Integer> {
    /**
     * 根据手机账号查找用户
     * @param account
     * @return
     */
    SysUser findSysUserByEmail(String account);

    SysUser findSysUserByUserId(Integer id);

    /**
     * 根据热度降序排列
     * JPQL语句
     * @return
     */
    @Query("select u FROM SysUser  u ORDER BY u.likeCount DESC ")
    List<SysUser> findHotUsers();

}
