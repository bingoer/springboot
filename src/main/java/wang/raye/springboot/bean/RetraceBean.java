package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RetraceBean {

    /** macd_cross里的rsi状态status : 21 */
    private boolean isLow;
    /** macd_cross里的rsi状态status : 22 */
    private boolean isUp;
    /** macd_cross里的rsi状态status : 23 */
    private boolean isBack;
    /** macd_cross里的rsi状态status : 24 */
    private boolean isRetrace;


}