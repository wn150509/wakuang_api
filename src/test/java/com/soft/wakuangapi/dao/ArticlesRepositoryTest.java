package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.entity.ConcernUser;
import com.soft.wakuangapi.service.ConcernService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticlesRepositoryTest {
    @Resource
    private ArticlesRepository articlesRepository;
    @Resource
    private ConcernRepository concernRepository;
    @Test
    public void findByTypeId() throws Exception {
        List<Articles>articles=articlesRepository.findByTypeId(1);
        List<Articles>articlesList=new ArrayList<>();
        for (int i=0;i<articles.size();i++){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date data=articles.get(i).getCreateTime();
            String  str=simpleDateFormat.format(data);
            System.out.println(str);
        }
        articles.forEach(articles1 -> System.out.println(articles1));
    }
    @Test
    public void findByTypetime() throws Exception {
//        System.out.println(articlesRepository.queryAticleList("你"));
    }

}