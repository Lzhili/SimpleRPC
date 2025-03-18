package com.scut;

import com.scut.protocol.HttpServer;
import com.scut.register.LocalRegister;

public class Provider {

    public static void main(String[] args) {

        //本地注册(多版本)
        LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        LocalRegister.register(HelloService.class.getName(), "2.0", HelloServiceImpl2.class);

        //Tomcat（也可以为Netty）
        HttpServer httpServer = new HttpServer();
        httpServer.start("127.0.0.1", 8080);
    }
}

