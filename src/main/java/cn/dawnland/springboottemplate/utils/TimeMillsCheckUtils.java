package cn.dawnland.springboottemplate.utils;

import com.ximuok.wb_ttyy.base.BaseModel;

/**
 * @author Cap_Sub
 */
public class TimeMillsCheckUtils {

    public static Boolean checkTimeMillis(BaseModel baseModel){
        if(baseModel.getTimeMillis() == null || "".equals(baseModel.getTimeMillis())){
            return true;
        }
        return false;
    }

}
