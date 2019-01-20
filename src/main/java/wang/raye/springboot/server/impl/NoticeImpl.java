/**
 * model业务实现类
 */
/**
 * @author Raye
 * @since 2016年9月21日20:58:46
 */
package wang.raye.springboot.server.impl;

import de.elbatya.cryptocoins.bittrexclient.BlockccInfoClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.NoticesInfo;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.NoticesInfoEntry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.model.AlertUser;
import wang.raye.springboot.model.AlertUserCriteria;
import wang.raye.springboot.model.Notice;
import wang.raye.springboot.model.NoticeCriteria;
import wang.raye.springboot.model.mapper.AlertUserMapper;
import wang.raye.springboot.model.mapper.NoticeMapper;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.server.NoticeServer;
import wang.raye.springboot.utils.DateUtils;
import wang.raye.springboot.utils.ParseUtils;
import wang.raye.springboot.utils.WxSendUtils;
import wang.raye.springboot.utils.send.WxSendMessageUtil;

import java.util.List;

@Repository
public class NoticeImpl implements NoticeServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private AlertUserMapper alertUserMapper;
    @Autowired
    private WxSendUtils wxSendUtils;
    @Autowired
    private WxSendMessageUtil wxSendMessageUtil;
    @Autowired
    private MapperServer mapperServer;



    @Override
    public boolean getNoticeInfo() {


        BlockccInfoClient infoClient = new BlockccInfoClient();
        // 调用api获取公告信息列表
        NoticesInfo noticesInfo = infoClient.getInfoApi().getNoticesInfo().unwrap();
        List<NoticesInfoEntry> noticesInfoList = noticesInfo.getData();

        //调db获取发送人sckey
        List<AlertUser> alertUsers= mapperServer.getAlertUsers();
        List<AlertUser> alertWorkWxUsers= mapperServer.getAlertWorkWxUsers();

        for (NoticesInfoEntry noticesInfoEntry: noticesInfoList) {
            List<Notice> noticeList = this.getNotice(noticesInfoEntry.get_id());
            if (null == noticeList || noticeList.size() == 0) {
                if (null != noticesInfoEntry) {
                    // 去掉emoji 表情符号
                    noticesInfoEntry.setTitle(ParseUtils.emojiFormat(noticesInfoEntry.getTitle()));

                    this.addNotice(noticesInfoEntry);
                    this.sendNotice(alertWorkWxUsers, noticesInfoEntry, noticesInfo.getInfoUrl());
                    this.sendNotice2(alertUsers, noticesInfoEntry, noticesInfo.getInfoUrl());
                }
            }
        }
        return false;
    }

    private List<Notice> getNotice(String noticeId) {
        NoticeCriteria cond = new NoticeCriteria();
        cond.createCriteria()
                .andNoticeIdEqualTo(noticeId);
        return noticeMapper.selectByExample(cond);
    }

    private int addNotice(NoticesInfoEntry noticesInfoEntry) {
        Notice notice = new Notice();
        notice.setNoticeId(noticesInfoEntry.get_id());
        notice.setFromMarket(noticesInfoEntry.getFrom());
        notice.setLang(noticesInfoEntry.getLang());
        notice.setZhName(noticesInfoEntry.getZh_name());
        notice.setTimestamp(DateUtils.format(noticesInfoEntry.getTimestamp()));
        notice.setTitle(noticesInfoEntry.getTitle());
        return noticeMapper.insert(notice);
    }

    /**
     * 发送公告提醒

     */
    private void sendNotice2(List<AlertUser> alertUsers, NoticesInfoEntry noticesInfoEntry, String infoUrl){
        for(AlertUser alertUser : alertUsers) {
            wxSendUtils.sendNotice(alertUser.getSckey(), noticesInfoEntry, infoUrl);
        }
    }

    /**
     * 发送公告提醒

     */
    private void sendNotice(List<AlertUser> alertWorkWxUsers, NoticesInfoEntry noticesInfoEntry, String infoUrl){
        String mobile = "";
        for (int i = 0; i < alertWorkWxUsers.size(); i++) {
            if (i == 0) {
                mobile = alertWorkWxUsers.get(i).getMobile();
            } else {
                mobile += "/" + alertWorkWxUsers.get(i).getMobile();
            }
        }

        String market = noticesInfoEntry.getFrom();
        if (StringUtils.isNotEmpty(noticesInfoEntry.getZh_name())) {
            market = noticesInfoEntry.getZh_name();
        }
        String title= "【"+market+"】" +""+StringUtils.trim(noticesInfoEntry.getTitle());
        String description = "<div class=\\\"gray\\\">"+DateUtils.format(noticesInfoEntry.getTimestamp())+"</div>";
        String url = infoUrl + noticesInfoEntry.get_id();
        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_NOTICE, WxSendMessageUtil.AGENTID_NOTICE, mobile);
    }



}