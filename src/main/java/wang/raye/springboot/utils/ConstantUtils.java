package wang.raye.springboot.utils;

import org.springframework.stereotype.Component;
import wang.raye.springboot.bean.PositionBean;

import java.util.ArrayList;
import java.util.List;

@Component
public final class ConstantUtils {
    public static String RSI_RETRACE_STATUS_NORMAL = "20";
    public static String RSI_RETRACE_STATUS_LOW = "21";
    public static String RSI_RETRACE_STATUS_UP = "22";
    public static String RSI_RETRACE_STATUS_BACK = "23";
    public static String RSI_RETRACE_STATUS_RETRACE = "24";

    public ConstantUtils() {
    }


}