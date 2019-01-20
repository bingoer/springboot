package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SarBean {

    private boolean longPos;
    /** 转向点指标(SAR)*/
    private double sar;
    private double ep;
    private double af;

    /** 时间 */
    private long time;


}