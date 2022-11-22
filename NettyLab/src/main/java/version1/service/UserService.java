package version1.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import version1.protocol.UserQueryParam;
import version1.protocol.UserQueryResponse;

@Service
public class UserService {
    public UserQueryResponse queryUserByName(UserQueryParam param) {
        return new UserQueryResponse("李军","男",20, BigDecimal.valueOf(20000));
    }
}
