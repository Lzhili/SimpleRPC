package com.scut.proxy;

import com.scut.common.Invocation;
import com.scut.protocol.HttpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static <T> T getProxy(Class interfaceClass) {
        //用户配置
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(interfaceClass.getName(),
                        method.getName(), method.getParameterTypes(), args);

                HttpClient httpClient = new HttpClient();
                String result = httpClient.send("127.0.0.1", 8080, invocation);
                return result;
            }
        });
        return (T) proxyInstance; //返回代理对象
    }
}
