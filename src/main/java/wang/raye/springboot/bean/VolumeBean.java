package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class VolumeBean {

    /** 差离率（DIF） */
    private double fast;
    /** 九日DIF平滑移动平均值 */
    private double slow;
    /** 柱状线（BAR） */
    private double vol;
    /** 时间 */
    private long time;


}