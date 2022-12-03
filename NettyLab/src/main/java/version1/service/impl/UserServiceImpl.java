package version1.service.impl;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import version1.protocol.UserQueryParam;
import version1.protocol.UserQueryResponse;
import version1.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserQueryResponse queryUserByName(UserQueryParam param) {
        return new UserQueryResponse("李军","男",20, BigDecimal.valueOf(20000));
    }
}
