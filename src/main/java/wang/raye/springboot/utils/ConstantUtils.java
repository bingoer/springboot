package wang.raye.springboot.utils;

import org.springframework.stereotype.Component;
import wang.raye.springboot.bean.PositionBean;

import java.util.ArrayList;
import java.util.List;

@Component
public final class ConstantUtils {

    //=================STATUS 状态 ================================
    public static String RSI_RETRACE_STATUS_NORMAL = "20";//正常
    public static String RSI_RETRACE_STATUS_LOW = "21";//低点
    public static String RSI_RETRACE_STATUS_UP = "22";//上涨
    public static String RSI_RETRACE_STATUS_BACK = "23";//回调
    public static String RSI_RETRACE_STATUS_RETRACE = "24";//回调低点

    public static String ANALYSIS_STATUS_LOW = "11";//低点
    public static String ANALYSIS_STATUS_LOW_RETRACE = "12";//回调低点
    public static String ANALYSIS_STATUS_HIGH = "21";//高点
    public static String ANALYSIS_STATUS_STOP = "30";//止盈止损

    //=================TYPE 类型 ================================
    public static String ANALYSIS_TYPE_BUY = "BUY";
    public static String ANALYSIS_TYPE_SELL = "SELL";
    public static String ANALYSIS_TYPE_LOW = "LOW";
    public static String ANALYSIS_TYPE_HIGH = "HIGH";
    public static String ANALYSIS_TYPE_STOP = "STOP";

    public ConstantUtils() {
    }


}