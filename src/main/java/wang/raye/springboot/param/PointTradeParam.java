package wang.raye.springboot.param;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class PointTradeParam {

    private String id;

    private String timestamp;

    private String base;

    private String period;



}