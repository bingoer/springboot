package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class VolatileBean {

    /** 爆涨还是暴跌 */
    private String status;
    /** 低点还是高点 */
    private String type;
    /** 幅度 */
    private double change;
    /** 最值 */
    private double maxPrice;

}