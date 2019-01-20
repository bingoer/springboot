package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class KlineWebDataTradeBean {


    private double amount;
    private double price;
    private BigDecimal tid;
    private BigDecimal time;
    private String type;

}