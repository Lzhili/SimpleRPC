package com.scut;

import com.scut.common.URL;
import com.scut.protocol.HttpServer;
import com.scut.register.LocalRegister;
import com.scut.register.MapRemoteRegister;

public class Provider {

    public static void main(String[] args) {

        //本地注册(多版本)
        LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
//        LocalRegister.register(HelloService.class.getName(), "2.0", HelloServiceImpl2.class);

        //远程注册中心
        //服务注册
        URL url = new URL("127.0.0.1", 8080);
        MapRemoteRegister.register(HelloService.class.getName(), url);

        //Tomcat（也可以为Netty）
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(), url.getPort());
    }
}

