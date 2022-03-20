package com.edu.util;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.crypto.digest.MD5;

public class Md5Utils {

    public static final String salt = "Wisdom_";

    public static String generatePassWdHash(String passWord){
        byte[] digest = new MD5().digest(salt + passWord, "utf-8");
        return Base64Encoder.encode(digest);
    }
}
