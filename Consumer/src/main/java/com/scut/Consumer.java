package com.scut;

import com.scut.common.Invocation;
import com.scut.protocol.HttpClient;
import com.scut.proxy.ProxyFactory;

public class Consumer {

    public static void main(String[] args) {

        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("lzl");
        System.out.println(result);
    }
}
