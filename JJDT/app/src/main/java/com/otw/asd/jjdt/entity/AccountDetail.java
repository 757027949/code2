package com.otw.asd.jjdt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class AccountDetail implements Serializable {

    /**
     * accountOrderList : [{"date":"2016-05","accountOrders":[{"accountId":"402880e5550468e601550499e4c80001","accountOrderAfterAmount":1000,"accountOrderAmount":1000,"accountOrderCurrentAmount":0,"accountOrderExplain":"账户充值","accountOrderId":"402880e55504b235015504ba2fde0000","accountOrderMethod":"zfb","accountOrderState":"已完成","accountOrderTime":"2016-05-31 10:52:38"}]}]
     * totalCount : 1
     */

    private int totalCount;
    /**
     * date : 2016-05
     * accountOrders : [{"accountId":"402880e5550468e601550499e4c80001","accountOrderAfterAmount":1000,"accountOrderAmount":1000,"accountOrderCurrentAmount":0,"accountOrderExplain":"账户充值","accountOrderId":"402880e55504b235015504ba2fde0000","accountOrderMethod":"zfb","accountOrderState":"已完成","accountOrderTime":"2016-05-31 10:52:38"}]
     */

    private List<AccountOrderListBean> accountOrderList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<AccountOrderListBean> getAccountOrderList() {
        return accountOrderList;
    }

    public void setAccountOrderList(List<AccountOrderListBean> accountOrderList) {
        this.accountOrderList = accountOrderList;
    }

    public static class AccountOrderListBean implements Serializable {
        private String date;
        /**
         * accountId : 402880e5550468e601550499e4c80001
         * accountOrderAfterAmount : 1000
         * accountOrderAmount : 1000
         * accountOrderCurrentAmount : 0
         * accountOrderExplain : 账户充值
         * accountOrderId : 402880e55504b235015504ba2fde0000
         * accountOrderMethod : zfb
         * accountOrderState : 已完成
         * accountOrderTime : 2016-05-31 10:52:38
         */

        private List<AccountDetailInfo> accountOrders;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<AccountDetailInfo> getAccountOrders() {
            return accountOrders;
        }

        public void setAccountOrders(List<AccountDetailInfo> accountOrders) {
            this.accountOrders = accountOrders;
        }
    }
}
