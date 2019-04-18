package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.BooksService;
import com.soft.wakuangapi.service.SysUserService;
import com.soft.wakuangapi.service.UserConcernService;
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
    @Resource
    private UserConcernService userConcernService;

    @RequestMapping(value = "/sign_in", method = RequestMethod.POST)
    public ResponseUtil signIn(@RequestBody LoginUser loginUser) {
        System.out.println(loginUser);
        return sysUserService.userLogin(loginUser);
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

    @RequestMapping(value = "/avatar",method = RequestMethod.POST)
    public void changeAvatar(@RequestBody ImageUser imageUser){
        sysUserService.getUser(imageUser.getName(),imageUser.getBase64());
    }

    @RequestMapping(value = "/queryuser",method = RequestMethod.POST)
    public ResponseUtil queryUser(@RequestBody SearchUser searchUser){
        return new ResponseUtil(0,"query user",sysUserService.querySysUserList(searchUser));
    }

    @RequestMapping(value = "/insertuser",method = RequestMethod.POST)
    public ResponseUtil insetUserConcern(@RequestBody UserUser userUser){
        return new ResponseUtil(0,"insert userConcern",userConcernService.insertUserConcern(userUser));
    }

    @RequestMapping(value = "/deleteuser",method = RequestMethod.POST)
    public ResponseUtil deleteUserConcern(@RequestBody UserUser userUser){
        return new ResponseUtil(0,"delete userConcern",userConcernService.deleteUserConcern(userUser));
    }

    @RequestMapping(value = "/concern",method = RequestMethod.POST)
    public ResponseUtil getUserStatus(@RequestBody UserUser userUser){
        return new ResponseUtil(0,"get user concern",userConcernService.getUserConcern(userUser));
    }

    @RequestMapping(value = "/userConcern",method = RequestMethod.POST)
    public ResponseUtil getConcernUser(@RequestBody SysUser loginUser){
        return new ResponseUtil(0,"get concern user",userConcernService.getConcernUser(loginUser.getUserId()));
    }
}
