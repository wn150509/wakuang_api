package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Books;
import com.soft.wakuangapi.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserConcernRepositoryTest {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private BooksRepository booksRepository;
    @Test
    public void findSysUserByPhone() throws Exception {
        List<Books>booksList=booksRepository.findAll();
        booksList.forEach(books -> System.out.println(books));
    }

    @Test
    public void register() {
        SysUser sysUser=new SysUser();
        sysUser.setEmail("00000");
        sysUser.setPassword("111");
        sysUserRepository.save(sysUser);
    }
}