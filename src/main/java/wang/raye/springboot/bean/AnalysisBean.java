package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class AnalysisBean {

    private Integer id;

    private String exchange;

    private String symbol;

    private String type;

    private String status;

    private double price;

    private double priceRmb;

    private String period;

    private String klineTime;

    private String createTime;

    private double change;

    private double volume;

    private double curPrice;

    private double highPrice;
    private double lowPrice;
    private double priceChangePercent;

    private String klineUrl;

    private double ma10;


}