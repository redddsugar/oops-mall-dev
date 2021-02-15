package com.oops.service.impl.center;

import com.oops.mapper.UsersMapper;
import com.oops.pojo.Users;
import com.oops.pojo.bo.CenterUserBO;
import com.oops.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月30日 15:55
 */
@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Autowired
    UsersMapper usersMapper;

    @Override
    public Users queryUserInfo(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users dbuser = usersMapper.selectByPrimaryKey(userId);
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO,updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        updateUser.setCreatedTime(dbuser.getCreatedTime());
        //由于前端不更新密码，所以记得把数据库里面的老密码拿出来，不要更新之后密码字段为空了
        updateUser.setPassword(dbuser.getPassword());
        updateUser.setFace(dbuser.getFace());
        usersMapper.updateByPrimaryKeySelective(updateUser);
        return updateUser;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateFace(String userId, String faceUrl) {
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setFace(faceUrl);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(updateUser);
        return updateUser;
    }


}
