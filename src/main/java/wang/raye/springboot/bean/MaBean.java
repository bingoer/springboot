package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class MaBean {

    /** 攻击线 */
    private double ma5;
    /** 操盘线 */
    private double ma10;
    /** 生命线 */
    private double ma30;
    /** 趋势线 */
    private double ma120;
    /** 时间 */
    private long time;


}