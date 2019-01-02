package cn.dawnland.springboottemplate.utils;

/**
 * 数组工具类
 */
public class ArrayUtils {

    /**
     * 判断是否存在数组
     * @param args
     * @param param
     * @return
     */
    public static Boolean isExist(String[] args, String param) {
        for (String s : args) {
            if (s.equals(param)) {
                return true;
            }
        }
        return false;
    }
}
