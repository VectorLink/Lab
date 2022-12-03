package version1.service;

import version1.protocol.UserQueryParam;
import version1.protocol.UserQueryResponse;

public interface UserService {
    UserQueryResponse queryUserByName(UserQueryParam param);
}
