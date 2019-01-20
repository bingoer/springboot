package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class QuotaBean {

    private MacdBean macdBean;
    private KdjBean kdjBean;
    private BollBean bollBean;
    private RsiBean rsiBean;
    private DojiBean dojiBean;
    private VolumeBean volumeBean;
    private MaBean maBean;
    private VrBean vrBean;
    private DmiBean dmiBean;
    private SarBean sarBean;
    private PriceBean priceBean;
    private DojiPriceBean dojiPriceBean;



}