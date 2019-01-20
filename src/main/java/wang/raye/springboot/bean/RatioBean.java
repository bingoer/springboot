package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RatioBean {

    private double kdjHigh;
    private double kdjLow;
    private double kdjMid;
    private double macdZero;
    private double rsiChaomai;
    private double rsiLow;
    private double rsiHigh;
    private double rsiMid;
    private double volChange;
    private double ma5;
    private double ma10;
    private double ma30;
    private double ma120;
    private double pdiUpRatio;
    private double adxUpRatio;
    private double dojiDifRatio;
    private double dojiDownCountRatio;
    private double dojiDownChangeRatio;
    private double dojiHhvRatio;

    private boolean kdjOpen;
    private boolean macdOpen;
    private boolean bollOpen;
    private boolean rsiOpen;
    private boolean dojiOpen;
    private boolean volumeOpen;
    private boolean maOpen;
    private boolean vrOpen;
    private boolean dmiOpen;
    private boolean sarOpen;



}