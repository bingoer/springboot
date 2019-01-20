package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class BollBean {

    /** 上轨线=中轨线＋两倍的标准差 */
    private double ub;
    /** 中轨线=N日的移动平均线 */
    private double boll;
    /** 下轨线=中轨线－两倍的标准差 */
    private double lb;
    /** 下轨线=中轨线－两倍的标准差 */
    private double lbPre;
    /** 时间 */
    private long time;


}