package wang.raye.springboot.utils.send;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
@Component
public class WxSendMessageUtil {

    //企业ID
    private static final String CORPID = "ww854f7832c96b9ea0";

    //应用的凭证密钥
    public static final String SECRETID_IDEX = "i0Ai6HZvwLasaD_lV6jUgGE5jVd2GTKZAG0wDXeiVZw";
    public static final String SECRETID_NOTICE = "S2y51Sze2Wb5R53-e74b8x9E2qRbsMzhBYd_oL1rKBo";
    public static final String SECRETID_BINANCE_BUY = "bXwkXWp3tPQDU8d4oNtojF9RPKi176ZNfQCQxCHb_gg";
    public static final String SECRETID_BINANCE_SELL = "jxmyoFpMLWiyCUw9Ei_2tvwjuGFZ0JOwG0D4eVkW6WI";
    public static final String SECRETID_BINANCE_LOW = "gwWROWjTAhInUT0i_bKNiA_zyb_O3zuETXAltbYFbjQ";
    public static final String SECRETID_BINANCE_HIGH = "CZBVF6idivZgk_yb7czVLc2nlNun4WpiB-wSwrp2XIw";
    public static final String SECRETID_BINANCE_STOP = "6gFk6-xsP_lCaqpqqWb18biyPwT-UFUP2aAa2hqzdLA";
    public static final String SECRETID_HUOBI_BUY = "SWHa9pKj-NmZ41iJKmV6L5VEdGfmiaR9nZUUQpUFVzQ";
    public static final String SECRETID_HUOBI_SELL = "qFnAdukquqqZGh6ozid5kSqBDYzko8ZM9GnKg25UqdQ";
    public static final String SECRETID_GATE_BUY = "6bngPdIwq_Lx1QQKDYKQv1i9RYURMoU_0LA3Dva4u1Y";
    public static final String SECRETID_GATE_SELL = "z3MtHmSbM6IUx8_c6-Ha5n3jdwuGapNnd5fXRE8A9b4";
    public static final String SECRETID_BITTREX_BUY = "RyCHfkwo31Y1UIWhdotcM3cgX02-xfTJ5NtyWv1_ygo";
    public static final String SECRETID_BITTREX_SELL = "TUpaY0aaFDSqxtrS-tlEwuXaEKmTOhz9RX0lSa5Dcos";
    public static final String SECRETID_OKEX_BUY = "DIqYcOk3xgZw3l040_4Z4_RmIegldo5YMz5-csdDF5E";
    public static final String SECRETID_OKEX_SELL = "kUy0WM94k4h2jziRO2lArAW0Hi3KnGATOmCEG4OEMuQ";
    public static final String SECRETID_BINANCE_CHANGE = "T42yDNo2HramC-gxJIQu5ODv0uGKDI7xaMCE8bb0Eo0";
    public static final String SECRETID_HUOBI_CHANGE = "c58g7qHKA9zeelJ0uMlXBBDr07Ij2OFx3ULWG7hTPPQ";
    public static final String SECRETID_GATE_CHANGE = "EXyk6O1ULwcwBQRahaB7AaSODR_ZRNKKBvunrA-l5lo";
    public static final String SECRETID_BITTREX_CHANGE = "dCYOlT5NGbP_a3LQPiGzp4DksWsHpaCHaUmnbcu-j_g";
    public static final String SECRETID_OKEX_CHANGE = "CQeoX9hKQPZrJWDn7gPV_r_QX0I1HLrKQHEE9u3dz4A";
    public static final String SECRETID_NEWCOIN = "vt2rwlSW_IO_ydbxurvOJzOKCiAVD76PfPofXWvDwoM";

    public static final String AGENTID_IDEX = "1000002";
    public static final String AGENTID_NOTICE = "1000009";
    public static final String AGENTID_BINANCE_BUY = "1000010";
    public static final String AGENTID_BINANCE_SELL = "1000011";
    public static final String AGENTID_BINANCE_LOW = "1000026";
    public static final String AGENTID_BINANCE_HIGH = "1000027";
    public static final String AGENTID_BINANCE_STOP = "1000028";
    public static final String AGENTID_HUOBI_BUY = "1000018";
    public static final String AGENTID_HUOBI_SELL = "1000019";
    public static final String AGENTID_GATE_BUY = "1000020";
    public static final String AGENTID_GATE_SELL = "1000021";
    public static final String AGENTID_BITTREX_BUY = "1000022";
    public static final String AGENTID_BITTREX_SELL = "1000023";
    public static final String AGENTID_OKEX_BUY = "1000024";
    public static final String AGENTID_OKEX_SELL = "1000025";
    public static final String AGENTID_BINANCE_CHANGE = "1000012";
    public static final String AGENTID_HUOBI_CHANGE = "1000013";
    public static final String AGENTID_GATE_CHANGE = "1000014";
    public static final String AGENTID_BITTREX_CHANGE = "1000015";
    public static final String AGENTID_OKEX_CHANGE = "1000016";
    public static final String AGENTID_NEWCOIN = "1000017";


    /**
     * 发送消息
     *
     * @param mobile  电话号码
     * @param message 消息内容
     * @return s
     */
    public String sendMessageByMobile(String mobile, String message) {
        String ac = this.getAccessToken(CORPID, SECRETID_IDEX);
        String s = this.getUserName(mobile, "1", ac);
        return this.sendMessage(this.getMessageContent(message, "1000002", 0, s), ac);
    }

    /**
     * 发送卡片消息
     * @param title 标题
     * @param description 消息详情
     * @param url 卡片消息链接
     * @param secretId 应用id
     * @param agentId 代理id
     * @param mobile 手机号(对应的单个用户)
     * @return 结果
     */
    public String sendMessageCard(String title, String description, String url, String secretId, String agentId, String mobile) {
        String ac = this.getAccessToken(CORPID, secretId);
        String userId = "";
        if (StringUtils.isNotEmpty(mobile)) {
            String[] mobiles = mobile.split("/");
            for (int i = 0; i < mobiles.length; i++) {
                if (i == 0) {
                    userId = this.getUserName(mobiles[i], "1", ac);
                } else {
                    userId += "|" + this.getUserName(mobiles[i], "1", ac);
                }
            }
        }
        return this.sendMessage(this.getMessageContentCard(title , description, url, agentId, userId, "",""), ac);
    }

    /**
     * @param content     消息体
     * @param accessToken 微信授权码
     * @return
     */
    public String sendMessage(String content, String accessToken) {
        String sendUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        return new HttpRequester().postBody(sendUrl, content);
    }

    /**
     * @param messege 消息内容
     * @param agentId 应用id
     * @param type    类型 1，发送给部门  0发送给用户
     * @param userId  用户id 或者部门id
     * @return s
     */
    public String getMessageContent(String messege, String agentId, int type, String userId) {
        String toWho = "   \"touser\": \"" + userId + "\",";
        if (type == 1) {
            toWho = "   \"toparty\": \"" + userId + "\",";
        }
        return "{" +
                toWho +
                "   \"msgtype\": \"text\"," +
                "   \"agentid\": " + agentId + "," +
                "   \"text\": {" +
                "       \"content\": \"" + messege + "\"" +
                "   }," +
                "   \"safe\":0" +
                "}";

    }

    /**
     *
     * @param title 是必须	标题，不超过128个字节，超过会自动截断
     * @param description 是必须 描述，不超过512个字节，超过会自动截断
     * @param agentId 是必须 企业应用的id，整型。可在应用的设置页面查看
     * @param userId 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
     * @param partyId 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
     * @param tagId 标签ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
     * @return s
     */
    public String getMessageContentCard(String title, String description, String url, String agentId, String userId, String partyId, String tagId) {
        String toUser = "   \"touser\": \"" + "@all" + "\",";
        if (StringUtils.isNotEmpty(userId)) {
            toUser = "   \"touser\": \"" + userId + "\",";
        }
        String toParty = "   \"toparty\": \"" + "@all" + "\",";
        if (StringUtils.isNotEmpty(partyId)) {
            toParty = "   \"toparty\": \"" + partyId + "\",";
        }
        String toTag = "   \"totag\": \"" + "@all" + "\",";
        if (StringUtils.isNotEmpty(tagId)) {
            toTag = "   \"totag\": \"" + tagId + "\",";
        }
        return "{" +
                toUser +
//                toParty +
//                toTag +
                "   \"msgtype\": \"textcard\"," +
                "   \"agentid\": " + agentId + "," +
                "   \"textcard\": {" +
                "       \"title\": \"" + title + "\"," +
                "       \"description\": \"" + description + "\"," +
                "       \"url\": \"" + url + "\"," +
                "       \"btntxt\": \"" + "更多" + "\"" +
                "   }" +
                "}";

    }

    /**
     * @param departMentId 部门id
     * @param accessToken  微信授权码
     * @return s
     */
    public Map<String, String> getUserMap(String departMentId, String accessToken) {

        String userUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/list?department_id=" + departMentId + "&fetch_child=1&access_token=" + accessToken;

        try {
            HttpRespons respons = new HttpRequester().sendGet(userUrl);
            JSONObject jsonObject = JSON.parseObject(respons.getContent());
            String userlist = jsonObject.getString("userlist");
            List<UserModel> li = JSON.parseArray(userlist, UserModel.class);
            return li.stream().collect(
                    Collectors.toMap(UserModel::getMobile, UserModel::getUserid));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>(0);
    }

    /**
     * 根据电话号码获取企业微信用户的username
     *
     * @param mobile       电话号码
     * @param departMentId 部门id
     * @param accessToken  微信授权码
     * @return username
     */
    public String getUserName(String mobile, String departMentId, String accessToken) {

        String userUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/list?department_id=" + departMentId + "&fetch_child=1&access_token=" + accessToken;
        try {
            HttpRespons respons = new HttpRequester().sendGet(userUrl);
            JSONObject jsonObject = JSON.parseObject(respons.getContent());
            String userlist = jsonObject.getString("userlist");
            List<UserModel> li = JSON.parseArray(userlist, UserModel.class);
            return li.stream().filter(user -> mobile.equals(user.getMobile())).map(UserModel::getUserid).collect(Collectors.toList()).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param corpId   企业号id
     * @param secretId 应用密钥
     * @return 微信授权码
     */
    public String getAccessToken(String corpId, String secretId) {

        String tokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpsecret=" + secretId + "&corpid=" + corpId;
        HttpRespons httpRespons;
        try {
            httpRespons = new HttpRequester().sendGet(tokenUrl);
            return JSON.parseObject(httpRespons.getContent()).getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
