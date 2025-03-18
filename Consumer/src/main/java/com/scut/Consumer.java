package com.scut;

import com.scut.common.Invocation;
import com.scut.protocol.HttpClient;

public class Consumer {

    public static void main(String[] args) {
        Invocation invocation = new Invocation(HelloService.class.getName(),
                "sayHello", new Class[]{String.class}, new Object[]{"lzl-lzl"});

        HttpClient httpClient = new HttpClient();
        String result = httpClient.send("127.0.0.1", 8080, invocation);
        System.out.println(result);
    }
}
