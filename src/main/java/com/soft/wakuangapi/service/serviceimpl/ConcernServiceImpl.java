package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.ArticlesRepository;
import com.soft.wakuangapi.dao.ConcernRepository;
import com.soft.wakuangapi.dao.LabelsRepository;
import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.entity.ConcernUser;
import com.soft.wakuangapi.entity.LabelStatus;
import com.soft.wakuangapi.entity.Labels;
import com.soft.wakuangapi.service.ConcernService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class ConcernServiceImpl implements ConcernService {
    @Resource
    private ConcernRepository concernRepository;
    @Resource
    private LabelsRepository labelsRepository;
    @Resource
    private ArticlesRepository articlesRepository;
    @Override
    public List<LabelStatus> getConcernLabels(Integer id) {
        List<Labels>labelsList=labelsRepository.findAll();
        List<ConcernUser>concernUsers=concernRepository.findAllByUserId(id);
        List<LabelStatus>labelStatusList=new ArrayList<>();
        for (int i=0;i<labelsList.size();i++){
            Labels labels=labelsList.get(i);
            List<ConcernUser>labelfanscount=concernRepository.findAllByLabelId(labelsList.get(i).getLabelsId());
            List<Articles>labelarticlecount=articlesRepository.findAllByLabelId(labelsList.get(i).getLabelsId());
            int status=0;
            for (int j=0;j<concernUsers.size();j++){
                if (concernUsers.get(j).getLabelId()==labelsList.get(i).getLabelsId()){
                    status=1;
                }
            }
            LabelStatus labelStatus=new LabelStatus(labels.getLabelsId(),
                    labels.getLabelsUrl(),labels.getLabelsName(),
                    labelarticlecount.size(),labelfanscount.size(),status);
            labelStatusList.add(labelStatus);
        }
        return labelStatusList;
    }

    @Override
    public ResponseUtil insertConcern(ConcernUser concernUser) {
        return new ResponseUtil(0,"insert concernlabel",concernRepository.save(concernUser));
    }

    @Override
    public ResponseUtil deleteConcern(ConcernUser concernUser) {
        return new ResponseUtil(0,"delete concernlabel",concernRepository.deleteConcernUserByUserIdAndLabelId(concernUser.getUserId(),concernUser.getLabelId()));
    }
}
