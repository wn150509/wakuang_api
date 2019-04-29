package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.PinCommentLikeRepository;
import com.soft.wakuangapi.dao.PinCommentRepository;
import com.soft.wakuangapi.dao.SysUserRepository;
import com.soft.wakuangapi.dao.UserConcernRepository;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.PinCommentService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Collections;

@Service
public class PinCommentServiceImpl implements PinCommentService {
    @Resource
    private PinCommentRepository pinCommentRepository;
    @Resource
    private PinCommentLikeRepository pinCommentLikeRepository;
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private UserConcernRepository userConcernRepository;

    @Override
    public ResponseUtil addPinComment(PinComment pinComment) {
        PinComment pinComment1=new PinComment();
        pinComment1.setCommentContent(pinComment.getCommentContent());
        pinComment1.setPinId(pinComment.getPinId());
        pinComment1.setUserId(pinComment.getUserId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date=null;
        try {
            date=df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pinComment1.setCommentTime(date);
        return new ResponseUtil(0,"add pinComment",pinCommentRepository.save(pinComment1));
    }

    @Override
    public ResponseUtil deltePinComment(PinComment pinComment) {
        return new ResponseUtil(0,"delete pinComment",pinCommentRepository.deletePinCommentByCommentId(pinComment.getCommentId()));
    }

    @Override
    public ResponseUtil getPinComments(PinUser pinUser) {
        return new ResponseUtil(0,"get pinComments",paixuTime(getPinCommentStatus(pinUser)));
    }

    @Override
    public ResponseUtil insertPinCommentLike(PinCommentLike pinCommentLike) {
        return new ResponseUtil(0,"insert pinCommentLike",pinCommentLikeRepository.save(pinCommentLike));
    }

    @Override
    public ResponseUtil deltePinCommentLike(PinCommentLike pinCommentLike) {
        return new ResponseUtil(0,"delete pinCommentLike",pinCommentLikeRepository.deletePinCommentLikeByCommentIdAndUserId(pinCommentLike.getCommentId(),pinCommentLike.getUserId()));
    }

    public List<PinCommentStatus>getPinCommentStatus(PinUser pinUser){
        List<PinComment>pinCommentList=pinCommentRepository.findAllByPinId(pinUser.getPinId());//找到谋篇沸点下的所有评论
        List<PinCommentLike>pinCommentLikes=pinCommentLikeRepository.findAllByUserId(pinUser.getUserId());//找到该用户点赞的所有沸点评论
        List<PinCommentStatus>pinCommentStatuses=new ArrayList<>();

        for (int i=0;i<pinCommentList.size();i++){
            PinComment pinComment=pinCommentList.get(i);
            int status=0;
            List<PinCommentLike>pinCommentLikeList=pinCommentLikeRepository.findAllByCommentId(pinComment.getCommentId());
            for (int j=0;j<pinCommentLikes.size();j++){
                if (pinComment.getCommentId().equals(pinCommentLikes.get(j).getCommentId())){
                    status=1;
                }
            }
            UserStatus userStatus=getOneUserStatus(pinUser.getUserId(),pinComment.getUserId());
            PinCommentStatus pinCommentStatus=new PinCommentStatus(pinComment.getCommentId(),pinComment.getPinId(),userStatus,
                    pinComment.getCommentContent(),pinComment.getCommentTime(),pinCommentLikeList.size(),status);
            pinCommentStatuses.add(pinCommentStatus);
        }
        return pinCommentStatuses;
    }

    public UserStatus getOneUserStatus(Integer userId, Integer concernedId){
        List<UserUser>userUsers=userConcernRepository.findAllByUserId(userId);//根据userId获取被关注用户ID组
        UserStatus userStatus=new UserStatus();
        SysUser sysUser=sysUserRepository.findSysUserByUserId(concernedId);//找到发布用户
        int status=0;
        for (int i=0;i<userUsers.size();i++){
            if (userUsers.get(i).getConcerneduserId().equals(concernedId)){
                status=1;
            }
        }
        userStatus=new UserStatus(sysUser.getUserId(),
                sysUser.getUserAvatar(),sysUser.getUserName(),
                sysUser.getDescription(),sysUser.getUserCompany(),sysUser.getUserPosition(),status);
        return userStatus;
    }

    public List<PinCommentStatus> paixuTime(List<PinCommentStatus>pinCommentStatuses){

        Collections.sort(pinCommentStatuses, new Comparator<PinCommentStatus>(){
            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            public int compare(PinCommentStatus o1, PinCommentStatus o2) {
                //进行降序排列
                //将date类型的转换为String类型，然后截取和库中相同的长度，再转换为int；这样的截取方式已比对过，无差异
                Integer dateValue1 = Integer.valueOf(String.valueOf(o1.getCommentTime().getTime()).substring(0, 10));
                Integer dataValue2 = Integer.valueOf(String.valueOf(o2.getCommentTime().getTime()).substring(0,10));
                if(dateValue1 < dataValue2){
                    return 1;
                }
                if(dateValue1 == dataValue2){
                    return 0;
                }
                return -1;
            }
        });

        return pinCommentStatuses;
    }
}
