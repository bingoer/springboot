package wang.raye.springboot.bean;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RsiBean {

    /** K线是快速确认线——数值在90以上为超买，数值在10以下为超卖 */
    private double rsi1;
    /** D线是慢速主干线——数值在80以上为超买，数值在20以下为超卖 */
    private double rsi2;
    /** J线为方向敏感线 */
    private double rsi3;
    /** 时间 */
    private long time;



}