package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class KlineWebDataBean {

    /** 攻击线 */
    private KlineWebDataDepBean depths;
    private List<List<Double>> lines;
    private List<KlineWebDataTradeBean> trades;

}