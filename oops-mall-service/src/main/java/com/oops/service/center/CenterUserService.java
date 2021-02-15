package com.oops.service.center;

import com.oops.pojo.Users;
import com.oops.pojo.bo.CenterUserBO;

/**
 * @Description TODO
 */
public interface CenterUserService {
    Users queryUserInfo(String userId);

    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    Users updateFace(String userId ,String faceUrl);
}
