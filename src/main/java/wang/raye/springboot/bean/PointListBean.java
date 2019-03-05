package wang.raye.springboot.bean;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class PointListBean {

    private List<Kline> lowList;
    private List<Kline> retraceList;
    private List<Kline> highList;
    private List<Kline> stopLimitList;

    private List<CrossQuotaBean> lowCrossQuotaList;
    private List<CrossQuotaBean> highCrossQuotaList;
    private List<CrossQuotaBean> stopLimitCrossQuotaList;
    private List<CrossQuotaBean> retraceCrossQuotaList;


}