package cn.dawnland.springboottemplate.utils;

import java.util.Random;

/**
 * @author Cap_Sub
 */
public class SmsUtils {

    public static String makeCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += random.nextInt(10);
        }
        random = null;
        return code;
    }

}
