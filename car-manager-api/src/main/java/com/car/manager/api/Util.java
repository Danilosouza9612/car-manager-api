package com.car.manager.api;

public class Util {
    public static String getExtension(String contentType){
        switch (contentType){
            case "image/jpeg":
                return ".jpg";
            case "image/png":
                return ".png";
        }
        return null;
    }
}
