package com.oops.service.impl;

import com.oops.enums.YesOrNo;
import com.oops.mapper.UserAddressMapper;
import com.oops.pojo.UserAddress;
import com.oops.pojo.bo.AddressBO;
import com.oops.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月23日 17:24
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    UserAddressMapper userAddressMapper;
    @Autowired
    Sid sid;

    @Override
    public List<UserAddress> queryAddress(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAddress(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty()) {
            isDefault = 1;
        }

        //生成ID插入userAddress至数据库
        String id = sid.nextShort();
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(id);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        //由于是update，所以已带有Id
        String addressId = addressBO.getAddressId();
        UserAddress userAddress = new UserAddress();

        //由于AddressBO与UserAddress ID属性名不同 无法赋值
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(addressId);
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);

        userAddressMapper.delete(address);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressStatus(String userId, String addressId) {
//        查找默认地址改为非默认状态   0:非默认   1:默认

        //查找该用户旗下所有状态为1的地址，改其状态为0   1->0
        UserAddress target = new UserAddress();
        target.setIsDefault(YesOrNo.NO.type);
        Example example = new Example(UserAddress.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("isDefault", YesOrNo.YES.type);
        userAddressMapper.updateByExampleSelective(target, example);

        //更改该地址为默认地址  0->1
        target.setId(addressId);
        target.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(target);
    }
}