package com.netty.version1.protocol;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserQueryResponse {
    private String userName;
    private String sex;
    private Integer age;
    private BigDecimal salary;
}
