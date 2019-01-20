package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class VrBean {

    /** 攻击线 */
    private double vr;
    /** 操盘线 */
    private double mavr;
    /** 时间 */
    private long time;


}