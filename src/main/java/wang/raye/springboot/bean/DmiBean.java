package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class DmiBean {

    /** 多空指标(+DI*/
    private double pdi;
    /** 多空指标(-DI) */
    private double mdi;
    /** 趋向指标(ADX、ADXR) */
    private double adx;
    /** 趋向指标(ADX、ADXR) */
    private double adxr;
    /** 趋向指标(ADX、ADXR) */
    private double adxPre;
    /** 时间 */
    private long time;


}