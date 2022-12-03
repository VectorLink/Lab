package com.netty.version1.service;

import com.netty.version1.protocol.UserQueryParam;
import com.netty.version1.protocol.UserQueryResponse;

public interface UserService {
    UserQueryResponse queryUserByName(UserQueryParam param);
}
