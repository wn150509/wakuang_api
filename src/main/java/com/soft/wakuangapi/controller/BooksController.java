package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.service.BooksService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/books")
@CrossOrigin("http://localhost:81")
public class BooksController {
    @Resource
    private BooksService booksService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseUtil getAllBooks(){
        return new ResponseUtil(0,"get user!",booksService.findAllBooks());
    }

    @RequestMapping(value = "/type1",method = RequestMethod.GET)
    public ResponseUtil getType1Books(){
        return new ResponseUtil(0,"get user!",booksService.findTypeBooks(1));
    }
    @RequestMapping(value = "/type2",method = RequestMethod.GET)
    public ResponseUtil getType2Books(){
        return new ResponseUtil(0,"get user!",booksService.findTypeBooks(2));
    }
    @RequestMapping(value = "/type3",method = RequestMethod.GET)
    public ResponseUtil getType3Books(){
        return new ResponseUtil(0,"get user!",booksService.findTypeBooks(3));
    }
    @RequestMapping(value = "/type4",method = RequestMethod.GET)
    public ResponseUtil getType4Books(){
        return new ResponseUtil(0,"get user!",booksService.findTypeBooks(4));
    }
    @RequestMapping(value = "/type5",method = RequestMethod.GET)
    public ResponseUtil getType5Books(){
        return new ResponseUtil(0,"get user!",booksService.findTypeBooks(5));
    }

}
