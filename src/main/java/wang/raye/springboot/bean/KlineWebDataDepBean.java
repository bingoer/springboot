package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class KlineWebDataDepBean {


    private KlineWebDataDepAskBean asks;
    /** 操盘线 */
    private KlineWebDataDepBidBean bids;


}