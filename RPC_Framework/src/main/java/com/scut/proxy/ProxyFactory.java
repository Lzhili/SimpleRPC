package com.scut.proxy;

import com.scut.common.Invocation;
import com.scut.common.URL;
import com.scut.loadbalance.LoadBalance;
import com.scut.protocol.HttpClient;
import com.scut.register.MapRemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactory {

    public static <T> T getProxy(Class interfaceClass) {
        //用户配置
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(interfaceClass.getName(),
                        method.getName(), method.getParameterTypes(), args);

                HttpClient httpClient = new HttpClient();

                //服务发现
                List<URL> list = MapRemoteRegister.get(interfaceClass.getName());

                String result = null;

                int maxRetries = 3; //服务重试最多3次
                List<URL> invokedUrls = new ArrayList<>(); //记录已经调用过的url

                while (maxRetries > 0) { //服务重试
                    //负载均衡
                    list.remove(invokedUrls);
                    URL url = LoadBalance.random(list);
                    invokedUrls.add(url);

                    try {
                        //服务调用
                        result = httpClient.send(url.getHostname(), url.getPort(), invocation);
                        if(result != null) break;
                    } catch (Exception e) {
                        System.out.println("try failed"); //尝试失败
                        if(--maxRetries != 0) continue;
                        // errorCallback，容错逻辑
                        return "Service not found, please start the provider first!";
                    }
                }
                return result;
            }
        });
        return (T) proxyInstance; //返回代理对象
    }
}
