package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CrossQuotaBean {

    private QuotaBean quotaBean;
    private QuotaBean quotaPreBean;
    private QuotaBean quotaThrBean;
    private String klineTime;
    private double price;
    private double pricePre;
    private double priceThr;
    private double highPrice;
    private double thrLowPrice;


}