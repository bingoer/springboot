package wang.raye.springboot.bean;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class QuotaListBean {

    private List<MacdBean> macdBeanList;
    private List<BollBean> bollBeanList;
    private List<KdjBean> kdjBeanList;
    private List<VolumeBean> volBeanList;
    private List<MaBean> maBeanList;
    private List<RsiBean> rsiBeanList;
    private List<DmiBean> dmiBeanList;
    private List<VrBean> vrBeanList;
    private List<SarBean> sarBeanList;
    private List<DojiBean> dojiBeanList;




}