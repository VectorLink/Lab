package com.netty.version1.protocol;

import com.netty.version1.common.APIId;

import lombok.Data;

@Data
public abstract class IResponse<T> {
  APIId apiId;
}
