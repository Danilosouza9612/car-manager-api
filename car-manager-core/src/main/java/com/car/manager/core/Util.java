package com.car.manager.core;

public class Util {
    public static String generateAvatarPath(String resource, long id, String extension){
        return resource + "-" + id + extension;
    }
}
