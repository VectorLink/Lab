package com.netty.version1.service.impl;

import java.math.BigDecimal;
import com.netty.version1.protocol.UserQueryParam;
import com.netty.version1.protocol.UserQueryResponse;
import com.netty.version1.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserQueryResponse queryUserByName(UserQueryParam param) {
        return new UserQueryResponse("李军","男",20, BigDecimal.valueOf(20000));
    }
}
