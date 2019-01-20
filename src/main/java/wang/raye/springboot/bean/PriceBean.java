package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class PriceBean {


    private double openPre;
    private double openPre2;
    private double openPre3;
    /** 缩量:=V<=HHV(V,10)*0.5; */
    private double closePre;
    private double closePre2;
    private double closePre3;
    /** J线为方向敏感线 */
    private double close;
    private double low;
    private double lowPre;


}