package cn.dawnland.springboottemplate.utils;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.ximuok.wb_ttyy.config.PayConfig;
import com.ximuok.wb_ttyy.models.Order;
import com.ximuok.wb_ttyy.models.Payment;
import com.ximuok.wb_ttyy.service.PaymentService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Cap_Sub
 */
@Component
public class PayUtils {

    //#	参数名	含义	类型	说明
    //1	version	API 版本号	string(24)	必填。目前为1.1
    //2	lang	显示语言	string(24)	必填。可选值：zh-cn,en-us
    //3	plugins	渠道ID	string(24)	必填。根据自己需要命名渠道ID，例如：myswebsite1,mywebsite2
    //4	appid	APP ID	string(32)	必填。应用ID
    //5	trade_order_id	商户订单号	string(32)	必填。请确保在当前网站内是唯一订单号
    //6	payment	支付方式	string(16)	必填。可选值：wechat,alipay
    //7	total_fee	订单金额(元)	decimal(18,2)	必填。单位为人民币，精确到分
    //8	title	订单标题	string(128)	必填。商户订单标题
    //9	time	当前时间戳	int(11)	必填。PHP示例：time()
    //10	notify_url	通知回调网址	string(128)	必填。用户支付成功后，我们服务器会主动发送一个post消息到这个网址(注意：当前接口内，SESSION内容无效)
    //11	return_url	跳转网址	string(128)	必填。用户支付成功后，我们会让用户浏览器自动跳转到这个网址
    //12	callback_url	商品网址	string(128)	选填。用户取消支付后，我们可能引导用户跳转到这个网址上重新进行支付
    //13	nonce_str	随机值	string(32)	必填。作用：1.避免服务器页面缓存，2.防止安全密钥被猜测出来
    //14	device_id	设备ID	string(4)	可选。支付请求发送到指定手机app
    //15	modal (新)	支付模式	string(16)	可空。可选值( full:返回完整的支付网页; qrcode:返回二维码; 空值:返回支付跳转链接)
    //16	hash	签名	string(32)	必填。

    @Autowired
    private PayConfig payConfig;
    @Autowired
    private PaymentService paymentService;

    private Logger logger = LoggerFactory.getLogger(PayConfig.class);

    public String createPayment(Long userId, String payment, Order order) throws Exception{
        Payment insertPayment = paymentService.findPaymentByOrderId(order.getId());;
        if(insertPayment == null){
            insertPayment = new Payment(userId, "xunhupay", "APP", payment, order.getFinalPrice(), order.getId());
            paymentService.insert(insertPayment);
        }
        Map<String, String> sortParams = new HashMap<>(14);
        sortParams.put("version", payConfig.getVersion());
        sortParams.put("lang", payConfig.getLang());
        sortParams.put("plugins", "ttyy");
        sortParams.put("appid", payConfig.getAppid());
        sortParams.put("trade_order_id", insertPayment.getPayNo());
        sortParams.put("payment", payment);
        sortParams.put("total_fee", order.getFinalPrice().doubleValue() + "");
        sortParams.put("title", "订单支付:" + insertPayment.getPayNo());
        sortParams.put("time", System.currentTimeMillis() / 1000 + "");
        sortParams.put("notify_url", payConfig.getNotifyUrl());
        sortParams.put("return_url", payConfig.getReturnUrl());
        sortParams.put("nonce_str", insertPayment.getPayNo());
        sortParams.put("modal", payConfig.getModal());
        String hash = createSign(sortParams, payConfig.getSign());
        sortParams.put("hash", hash);
        logger.info("sortParams" +sortParams);
        String response = UrlUtil.sendPost("https://pay2.xunhupay.com/v2/payment/do.html", sortParams);
        JSONObject jsonObject = JSONObject.parseObject(response);
        return (String)jsonObject.get("url");
    }

    public String createPaymentNew(Long userId, String payment, Order order) throws Exception{
        Payment insertPayment = paymentService.findPaymentByOrderId(order.getId());;
        if(insertPayment == null){
            insertPayment = new Payment(userId, "xunhupay", "APP", payment, order.getFinalPrice(), order.getId());
            paymentService.insert(insertPayment);
        }
        Map<String, String> sortParams = new HashMap<>(14);
        sortParams.put("version", payConfig.getVersion());
        sortParams.put("lang", payConfig.getLang());
        sortParams.put("plugins", "ttyy");
        sortParams.put("appid", payConfig.getAppid());
        sortParams.put("trade_order_id", insertPayment.getPayNo());
        sortParams.put("payment", payment);
        sortParams.put("total_fee", order.getFinalPrice().doubleValue() + "");
        sortParams.put("title", "订单支付:" + insertPayment.getPayNo());
        sortParams.put("time", System.currentTimeMillis() / 1000 + "");
        sortParams.put("notify_url", payConfig.getNotifyUrl());
        sortParams.put("return_url", payConfig.getReturnUrl());
        sortParams.put("nonce_str", insertPayment.getPayNo());
        String hash = createSign(sortParams, payConfig.getSign());
        sortParams.put("modal", "full");
        sortParams.put("hash", hash);
        logger.info("sortParams" +sortParams);
        String response = UrlUtil.sendPost("https://pay2.xunhupay.com/v2/payment/do.html", sortParams);
        JSONObject jsonObject = JSONObject.parseObject(response);
        return (String)jsonObject.get("html");
    }

    public String createSign(Map<String, String> params, String privateKey){
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams=sortMapByKey(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().trim();
            if (!StringUtils.isEmpty(value)) {
                sb.append("&").append(key).append("=").append(value);
            }
        }
        String stringA = sb.toString().replaceFirst("&", "");
        String stringSignTemp = stringA + privateKey;
        String signValue = DigestUtils.md5DigestAsHex(stringSignTemp.getBytes());
        return signValue;
    }

    public Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    public class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

}
