package com.oops.controller;

import com.oops.pojo.UserAddress;
import com.oops.pojo.bo.AddressBO;
import com.oops.service.AddressService;
import com.oops.utils.OopsResult;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月23日 17:12
 */
@Api(value = "收货地址相关controller", tags = {"收货地址相关接口"})
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @PostMapping("/list")
    public OopsResult getAddress(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return OopsResult.errorMsg("");
        }
        List<UserAddress> list = addressService.queryAddress(userId);
        return OopsResult.ok(list);
    }

    @PostMapping("/add")
    public OopsResult AddAddress(@RequestBody AddressBO addressBO) {
        OopsResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }

        addressService.addNewUserAddress(addressBO);

        return OopsResult.ok();
    }

    @PostMapping("/update")
    public OopsResult updateAddress(@RequestBody AddressBO addressBO) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return OopsResult.errorMsg("AddressId为空");
        }
        OopsResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }

        addressService.updateUserAddress(addressBO);
        return OopsResult.ok();
    }

    @PostMapping("/delete")
    public OopsResult deleteAddress(@RequestParam String userId, @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return OopsResult.errorMsg("");
        }
        addressService.deleteUserAddress(userId, addressId);
        return OopsResult.ok();
    }

    @PostMapping("/setDefault")
    public OopsResult setDefault(@RequestParam String userId, @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return OopsResult.errorMsg("");
        }
        addressService.updateUserAddressStatus(userId, addressId);
        return OopsResult.ok();
    }

    //判收货信息格式的合法性
    private OopsResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return OopsResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return OopsResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return OopsResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return OopsResult.errorMsg("收货人手机号长度不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return OopsResult.errorMsg("收货地址信息不能为空");
        }

        return OopsResult.ok();
    }
}
