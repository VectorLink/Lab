package service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import lombok.extern.slf4j.Slf4j;
import version1.common.APIId;
import version1.protocol.UserQueryParam;

@Slf4j
public class TestService {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        final UserQueryParam userQueryParam = new UserQueryParam(1L);
        final APIId apiId = userQueryParam.getApiId();
        final Class<?> name = Class.forName(apiId.getNamespace());
        log.debug("{}", name);
        final Method method = name.getMethod(apiId.getName(),UserQueryParam.class);
        log.debug("{}",method );
        final Object instance = name.newInstance();
        log.debug("{}",instance );
        final Object invoke = method.invoke(instance, userQueryParam);
        log.debug("{}",invoke );

    }


}
