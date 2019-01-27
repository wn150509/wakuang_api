package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.ArticlesRepository;
import com.soft.wakuangapi.dao.ConcernRepository;
import com.soft.wakuangapi.dao.LabelsRepository;
import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.entity.ConcernUser;
import com.soft.wakuangapi.entity.LabelStatus;
import com.soft.wakuangapi.entity.Labels;
import com.soft.wakuangapi.service.LabelsService;
import com.soft.wakuangapi.vo.LabelVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class LabelsServiceImpl implements LabelsService {
    @Resource
    private LabelsRepository labelsRepository;
    @Resource
    private ConcernRepository concernRepository;
    @Resource
    private ArticlesRepository articlesRepository;
    @Override
    public List<Labels> findAllLabels() {
        return labelsRepository.findAll();
    }

    @Override
    public LabelVo getonelabel(Integer labelid,Integer userid) {
        ConcernUser concernUser=concernRepository.findConcernUserByLabelIdAndUserId(labelid,userid);
        List<ConcernUser>labelfanscount=concernRepository.findAllByLabelId(labelid);
        List<Articles>labelarticlecount=articlesRepository.findAllByLabelId(labelid);
        int status=0;
        if (concernUser!=null){
            status=1;
        }
        Labels labels=labelsRepository.findLabelsByLabelsId(labelid);
        LabelStatus labelStatus=new LabelStatus(labelid,labels.getLabelsUrl(),
                labels.getLabelsName(),labelarticlecount.size(),labelfanscount.size(),status);
        LabelVo labelVo=new LabelVo(labelStatus,labelarticlecount);
        return labelVo;
    }

    @Override
    public Labels getoneLabel(String name) {
        return labelsRepository.findLabelsByLabelsName(name);
    }

}
