package com.scut.register;

import java.util.HashMap;
import java.util.Map;

//本地注册
public class LocalRegister {

    //接口名字 --> 映射 --> 实现类
    private static Map<String, Class> map = new HashMap<>();

    public static void register(String interfaceName, String version, Class implClass) {
        map.put(interfaceName + version, implClass);
    }

    public static Class get(String interfaceName, String version) {
        return map.get(interfaceName + version);
    }
}
