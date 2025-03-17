package com.scut;


import com.scut.protocol.HttpServer;

public class Provider {

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.start("127.0.0.1", 8080);
    }
}

