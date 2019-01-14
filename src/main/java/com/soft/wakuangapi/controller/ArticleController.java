package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.service.ArticleService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/articles")
@CrossOrigin("http://localhost:81")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/type0",method = RequestMethod.GET)
    public ResponseUtil getType0Articles(){
        return new ResponseUtil(0,"get type0",articleService.findTypearticle(0));
    }
    @RequestMapping(value = "/type0/time",method = RequestMethod.GET)
    public ResponseUtil getType0time(){
        return new ResponseUtil(0,"get type0",articleService.findbytime(0));
    }
    @RequestMapping(value = "/type0/comment",method = RequestMethod.GET)
    public ResponseUtil getType0comment(){
        return new ResponseUtil(0,"get type0",articleService.findbycomment(0));
    }

    @RequestMapping(value = "/type1",method = RequestMethod.GET)
    public ResponseUtil getType1Articles(){
        return new ResponseUtil(0,"get type1",articleService.findTypearticle(1));
    }
    @RequestMapping(value = "/type1/time",method = RequestMethod.GET)
    public ResponseUtil getType1time(){
        return new ResponseUtil(0,"get type1",articleService.findbytime(1));
    }
    @RequestMapping(value = "/type1/comment",method = RequestMethod.GET)
    public ResponseUtil getType1comment(){
        return new ResponseUtil(0,"get type1",articleService.findbycomment(1));
    }

    @RequestMapping(value = "/type2",method = RequestMethod.GET)
    public ResponseUtil getType2Articles(){
        return new ResponseUtil(0,"get type2",articleService.findTypearticle(2));
    }
    @RequestMapping(value = "/type2/time",method = RequestMethod.GET)
    public ResponseUtil getType2time(){
        return new ResponseUtil(0,"get type2",articleService.findbytime(2));
    }
    @RequestMapping(value = "/type2/comment",method = RequestMethod.GET)
    public ResponseUtil getType2comment(){
        return new ResponseUtil(0,"get type2",articleService.findbycomment(2));
    }

    @RequestMapping(value = "/type3",method = RequestMethod.GET)
    public ResponseUtil getType3Articles(){
        return new ResponseUtil(0,"get type3",articleService.findTypearticle(3));
    }
    @RequestMapping(value = "/type3/time",method = RequestMethod.GET)
    public ResponseUtil getType3time(){
        return new ResponseUtil(0,"get type3",articleService.findbytime(3));
    }
    @RequestMapping(value = "/type3/comment",method = RequestMethod.GET)
    public ResponseUtil getType3comment(){
        return new ResponseUtil(0,"get type3",articleService.findbycomment(3));
    }

    @RequestMapping(value = "/type4",method = RequestMethod.GET)
    public ResponseUtil getType4Articles(){
        return new ResponseUtil(0,"get type4",articleService.findTypearticle(4));
    }
    @RequestMapping(value = "/type4/time",method = RequestMethod.GET)
    public ResponseUtil getType4time(){
        return new ResponseUtil(0,"get type4",articleService.findbytime(4));
    }
    @RequestMapping(value = "/type4/comment",method = RequestMethod.GET)
    public ResponseUtil getType4comment(){
        return new ResponseUtil(0,"get type4",articleService.findbycomment(4));
    }

    @RequestMapping(value = "/type5",method = RequestMethod.GET)
    public ResponseUtil getType5Articles(){
        return new ResponseUtil(0,"get type5",articleService.findTypearticle(5));
    }
    @RequestMapping(value = "/type5/time",method = RequestMethod.GET)
    public ResponseUtil getType5time(){
        return new ResponseUtil(0,"get type5",articleService.findbytime(5));
    }
    @RequestMapping(value = "/type5/comment",method = RequestMethod.GET)
    public ResponseUtil getType5comment(){
        return new ResponseUtil(0,"get type5",articleService.findbycomment(5));
    }

    @RequestMapping(value = "/type6",method = RequestMethod.GET)
    public ResponseUtil getType6Articles(){
        return new ResponseUtil(0,"get type6",articleService.findTypearticle(6));
    }
    @RequestMapping(value = "/type6/time",method = RequestMethod.GET)
    public ResponseUtil getType6time(){
        return new ResponseUtil(0,"get type6",articleService.findbytime(6));
    }
    @RequestMapping(value = "/type6/comment",method = RequestMethod.GET)
    public ResponseUtil getType6comment(){
        return new ResponseUtil(0,"get type6",articleService.findbycomment(6));
    }
    @RequestMapping(value = "/type7",method = RequestMethod.GET)
    public ResponseUtil getType7Articles(){
        return new ResponseUtil(0,"get type7",articleService.findTypearticle(7));
    }
    @RequestMapping(value = "/type7/time",method = RequestMethod.GET)
    public ResponseUtil getType7time(){
        return new ResponseUtil(0,"get type7",articleService.findbytime(7));
    }
    @RequestMapping(value = "/type7/comment",method = RequestMethod.GET)
    public ResponseUtil getType7comment(){
        return new ResponseUtil(0,"get type7",articleService.findbycomment(7));
    }
    @RequestMapping(value = "/type8",method = RequestMethod.GET)
    public ResponseUtil getType8Articles(){
        return new ResponseUtil(0,"get type8",articleService.findTypearticle(8));
    }
    @RequestMapping(value = "/type8/time",method = RequestMethod.GET)
    public ResponseUtil getType8time(){
        return new ResponseUtil(0,"get type8",articleService.findbytime(8));
    }
    @RequestMapping(value = "/type8/comment",method = RequestMethod.GET)
    public ResponseUtil getType8comment(){
        return new ResponseUtil(0,"get type8",articleService.findbycomment(8));
    }
    @RequestMapping(value = "/type9",method = RequestMethod.GET)
    public ResponseUtil getType9Articles(){
        return new ResponseUtil(0,"get type9",articleService.findTypearticle(9));
    }
    @RequestMapping(value = "/type9/time",method = RequestMethod.GET)
    public ResponseUtil getType9time(){
        return new ResponseUtil(0,"get type9",articleService.findbytime(9));
    }
    @RequestMapping(value = "/type9/comment",method = RequestMethod.GET)
    public ResponseUtil getType9comment(){
        return new ResponseUtil(0,"get type9",articleService.findbycomment(9));
    }
    @RequestMapping(value = "/type10",method = RequestMethod.GET)
    public ResponseUtil getType10Articles(){
        return new ResponseUtil(0,"get type10",articleService.findTypearticle(10));
    }
    @RequestMapping(value = "/type10/time",method = RequestMethod.GET)
    public ResponseUtil getType10time(){
        return new ResponseUtil(0,"get type10",articleService.findbytime(10));
    }
    @RequestMapping(value = "/type10/comment",method = RequestMethod.GET)
    public ResponseUtil getType10comment(){
        return new ResponseUtil(0,"get type10",articleService.findbytime(10));
    }

}
