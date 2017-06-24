package com.otw.asd.jjd.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
public class Charge implements Serializable {

    /**
     * amount : 100
     * amountRefunded : 0
     * amountSettle : 100
     * app : app_XbPmH04Cmn9CvvrX
     * body : 课程报名
     * channel : alipay
     * clientIp : 127.0.0.1
     * created : 1467707038
     * credential : {"object":"credential","alipay":{"orderInfo":"_input_charset=\"utf-8\"&body=\"课程报名\"&it_b_pay=\"2016-07-06 16:23:58\"&notify_url=\"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_X14qj9er90u19yfjj9zfrvnP\"&out_trade_no=\"1467707034671pr79rn8\"&partner=\"2008870196482065\"&payment_type=\"1\"&seller_id=\"2008870196482065\"&service=\"mobile.securitypay.pay\"&subject=\"pay\"&total_fee=\"1.00\"&sign=\"T3FQU3VQU2ExOGlIbjVPNE9HeXJYYkRD\"&sign_type=\"RSA\""}}
     * currency : cny
     * description : null
     * extra : {}
     * failureCode : null
     * failureMsg : null
     * id : ch_X14qj9er90u19yfjj9zfrvnP
     * livemode : false
     * metadata : {}
     * object : charge
     * orderNo : 1467707034671pr79rn8
     * paid : false
     * refunded : false
     * refunds : {"URL":"/v1/charges/ch_X14qj9er90u19yfjj9zfrvnP/refunds","data":[],"hasMore":false,"object":"list"}
     * subject : pay
     * timeExpire : 1467793438
     * timePaid : null
     * timeSettle : null
     * transactionNo : null
     */

    private int amount;
    private int amountRefunded;
    private int amountSettle;
    private String app;
    private String body;
    private String channel;
    private String clientIp;
    private int created;
    /**
     * object : credential
     * alipay : {"orderInfo":"_input_charset=\"utf-8\"&body=\"课程报名\"&it_b_pay=\"2016-07-06 16:23:58\"&notify_url=\"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_X14qj9er90u19yfjj9zfrvnP\"&out_trade_no=\"1467707034671pr79rn8\"&partner=\"2008870196482065\"&payment_type=\"1\"&seller_id=\"2008870196482065\"&service=\"mobile.securitypay.pay\"&subject=\"pay\"&total_fee=\"1.00\"&sign=\"T3FQU3VQU2ExOGlIbjVPNE9HeXJYYkRD\"&sign_type=\"RSA\""}
     */

    private CredentialBean credential;
    private String currency;
    private Object description;
    private ExtraBean extra;
    private Object failureCode;
    private Object failureMsg;
    private String id;
    private boolean livemode;
    private MetadataBean metadata;
    private String object;
    private String orderNo;
    private boolean paid;
    private boolean refunded;
    /**
     * URL : /v1/charges/ch_X14qj9er90u19yfjj9zfrvnP/refunds
     * data : []
     * hasMore : false
     * object : list
     */

    private RefundsBean refunds;
    private String subject;
    private int timeExpire;
    private Object timePaid;
    private Object timeSettle;
    private Object transactionNo;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(int amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public int getAmountSettle() {
        return amountSettle;
    }

    public void setAmountSettle(int amountSettle) {
        this.amountSettle = amountSettle;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public CredentialBean getCredential() {
        return credential;
    }

    public void setCredential(CredentialBean credential) {
        this.credential = credential;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public Object getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(Object failureCode) {
        this.failureCode = failureCode;
    }

    public Object getFailureMsg() {
        return failureMsg;
    }

    public void setFailureMsg(Object failureMsg) {
        this.failureMsg = failureMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public RefundsBean getRefunds() {
        return refunds;
    }

    public void setRefunds(RefundsBean refunds) {
        this.refunds = refunds;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(int timeExpire) {
        this.timeExpire = timeExpire;
    }

    public Object getTimePaid() {
        return timePaid;
    }

    public void setTimePaid(Object timePaid) {
        this.timePaid = timePaid;
    }

    public Object getTimeSettle() {
        return timeSettle;
    }

    public void setTimeSettle(Object timeSettle) {
        this.timeSettle = timeSettle;
    }

    public Object getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(Object transactionNo) {
        this.transactionNo = transactionNo;
    }

    public static class CredentialBean {
        private String object;
        /**
         * orderInfo : _input_charset="utf-8"&body="课程报名"&it_b_pay="2016-07-06 16:23:58"&notify_url="https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_X14qj9er90u19yfjj9zfrvnP"&out_trade_no="1467707034671pr79rn8"&partner="2008870196482065"&payment_type="1"&seller_id="2008870196482065"&service="mobile.securitypay.pay"&subject="pay"&total_fee="1.00"&sign="T3FQU3VQU2ExOGlIbjVPNE9HeXJYYkRD"&sign_type="RSA"
         */

        private AlipayBean alipay;

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public AlipayBean getAlipay() {
            return alipay;
        }

        public void setAlipay(AlipayBean alipay) {
            this.alipay = alipay;
        }

        public static class AlipayBean {
            private String orderInfo;

            public String getOrderInfo() {
                return orderInfo;
            }

            public void setOrderInfo(String orderInfo) {
                this.orderInfo = orderInfo;
            }
        }
    }

    public static class ExtraBean {
    }

    public static class MetadataBean {
    }

    public static class RefundsBean {
        private String URL;
        private boolean hasMore;
        private String object;
        private List<?> data;

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }
}
