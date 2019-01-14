package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.Books;
import com.soft.wakuangapi.entity.LoginUser;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.service.BooksService;
import com.soft.wakuangapi.service.SysUserService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin("http://localhost:81")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private BooksService booksService;

    @RequestMapping(value = "/sign_in", method = RequestMethod.POST)
    public ResponseUtil signIn(@RequestBody LoginUser loginUser) {
        System.out.println(loginUser);return sysUserService.userLogin(loginUser);

    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public ResponseUtil signUp(@RequestBody SysUser sysUser) {
        return sysUserService.register(sysUser);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseUtil updateUser(@RequestBody SysUser sysUser) {
        return sysUserService.updateUser(sysUser);
    }

    @RequestMapping(value = "/hot", method = RequestMethod.GET)
    public ResponseUtil getHotUsers() {
        return sysUserService.findHotUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseUtil getOneUser(@PathVariable Integer id) {
//        Books books=booksService.findBooks(id);
        SysUser sysUser=sysUserService.findUser(id);
        return new ResponseUtil(0,"get user",sysUser);
    }
}
