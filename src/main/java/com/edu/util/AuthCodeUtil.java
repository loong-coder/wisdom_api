package com.edu.util;

import java.util.Random;

public class AuthCodeUtil {
	public static final int AUTHCODE_LENGTH = 5; // length of verification code  
    public static final int SINGLECODE_WIDTH = 10; // width of one digit or character in the image  
    public static final int SINGLECODE_HEIGHT = 25; // height of one digit or character in the image  
    public static final int SINGLECODE_GAP = 5; // margin of a digit or character  
    public static final int PADDING_TOP_BOT = 10;// padding of top and bottom
    public static final int PADDING_LEFT_RIGHT = 10; //padding of left and right
    public static final int IMG_WIDTH = AUTHCODE_LENGTH * (SINGLECODE_WIDTH + SINGLECODE_GAP) + PADDING_LEFT_RIGHT;  
    public static final int IMG_HEIGHT = SINGLECODE_HEIGHT+PADDING_TOP_BOT;  
    public static final char[] CHARS = {'0','1', '2', '3', '4', '5', '6', '7', '8',  
        '9','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',  
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',  
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',  
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };  
    static Random random = new Random();  
      

    public static String getAuthCode() {  
        StringBuffer buffer = new StringBuffer();  
        for (int i = 0; i < 5; i++) {// generate 6 digit and character  
            buffer.append(CHARS[random.nextInt(CHARS.length)]);  
        }  
        return buffer.toString();  
    }
}