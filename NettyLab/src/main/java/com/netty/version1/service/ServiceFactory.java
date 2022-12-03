package com.netty.version1.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import org.reflections.Reflections;

import lombok.extern.log4j.Log4j2;
import com.netty.version1.protocol.UserQueryParam;

@Log4j2
public class ServiceFactory {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        final Object instance = getInstance("version1.service","UserService");
    }
    private static Object getInstance(String interfacePath,String interfaceName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final Class clazz = Class.forName(interfacePath +"."+ interfaceName);
        Reflections reflections = new Reflections(clazz.getPackage().getName());
        final Set<Class> subTypesOf = reflections.getSubTypesOf(clazz);

        for (Class<? > aClass : subTypesOf) {
            log.info("{}",aClass);
            if (!aClass.isInterface()){
                Object newInstance = aClass.newInstance();
                final Method queryUserByName = aClass.getMethod("queryUserByName", UserQueryParam.class);

                final Object invoke = queryUserByName.invoke(newInstance, new UserQueryParam(300L));
                log.info("{}",invoke);
            }
        }
        return null;
    }
}
