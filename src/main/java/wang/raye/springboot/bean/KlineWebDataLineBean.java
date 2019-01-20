package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class KlineWebDataLineBean {

    /** 攻击线 */
    private KlineWebDataDepBean depths;

}