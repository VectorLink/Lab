package com.netty.version1.protocol;

import lombok.Data;
import com.netty.version1.common.APIId;

@Data
public class UserQueryParam extends IResponse<UserQueryResponse>{
    private Long userId;

    public UserQueryParam(Long userId) {
        this.apiId=new APIId("version1.service.UserService","queryUserByName");
        this.userId=userId;
    }
}
