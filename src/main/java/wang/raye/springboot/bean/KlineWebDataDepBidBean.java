package wang.raye.springboot.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class KlineWebDataDepBidBean {

    private List<double[]> bid;


}