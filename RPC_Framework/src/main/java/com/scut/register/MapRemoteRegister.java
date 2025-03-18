package com.scut.register;

import com.scut.common.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//远程注册中心
public class MapRemoteRegister {

    //接口名字 --> 映射 --> List<URL>
    private static Map<String, List<URL>> map = new HashMap<>();

    public static void register(String interfaceName, URL url) {
        List<URL> list = map.get(interfaceName);
        if(list == null){
            list = new ArrayList<>();
        }
        list.add(url);
        map.put(interfaceName, list);

        saveFile();
    }

    public static List<URL> get(String interfaceName) {
        map = getFile();
        return map.get(interfaceName);
    }

    private static void saveFile() {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("/map.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, List<URL>> getFile(){
        try{
            FileInputStream fileInputStream = new FileInputStream("/map.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
