package com.oops.service.impl;

import com.oops.enums.Sex;
import com.oops.mapper.UsersMapper;
import com.oops.pojo.Users;
import com.oops.pojo.bo.UserBo;
import com.oops.service.UserService;
import com.oops.utils.DateUtil;
import com.oops.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月19日 13:57
 */
@Service
public class UserServiceImpl implements UserService {
    private final String USER_FACE = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2594185022,2104800360&fm=26&gp=0.jpg";
    @Autowired
    UsersMapper userMapper;
    @Autowired
    Sid sid;

    @Override
    public boolean queryUsernameIsExist(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("username", username);
        Users users = userMapper.selectOneByExample(example);
        return users == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBo userBo) {
//        工具类生成id
        String id = sid.nextShort();
        Users user = new Users();
        user.setId(id);
        user.setUsername(userBo.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        默认值
        user.setFace(USER_FACE);
        user.setNickname(userBo.getUsername());
        user.setBirthday(DateUtil.stringToDate("2021-01-01"));
        user.setSex(Sex.SECRET.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        userMapper.insert(user);
        return user;
    }

    @Override
    public Users queryUserForLogin(String username, String password) throws Exception {
        Example example = new Example(Users.class);
        example.createCriteria().andEqualTo("username", username)
                .andEqualTo("password", MD5Utils.getMD5Str(password));
        return userMapper.selectOneByExample(example);
    }
}
