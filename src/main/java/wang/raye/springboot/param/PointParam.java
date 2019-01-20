package wang.raye.springboot.param;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class PointParam {

    private String exchange;

    private String symbol;

    private String base;

    private String period;

    private String status;

    private String quantType;

    private List<String> timestamp;



}