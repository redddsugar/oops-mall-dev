package com.oops.service;

import com.oops.pojo.UserAddress;
import com.oops.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author L-N
 */
public interface AddressService {

    List<UserAddress> queryAddress(String userId);

    void addNewUserAddress(AddressBO addressBO);

    void updateUserAddress(AddressBO addressBO);

    void deleteUserAddress(String userId, String addressId);

    /*
    修改收货地址状态为默认地址
     */
    void updateUserAddressStatus(String userId, String addressId);
}
