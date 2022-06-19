package com.wlsup.yygh.order.service;

import com.wlsup.yygh.model.order.OrderInfo;
import com.wlsup.yygh.model.order.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.Map;

public interface PaymentService extends IService<PaymentInfo> {

    //向支付记录表添加信息
    void savePaymentInfo(OrderInfo order, Integer status);

    //支付成功   更新订单状态
    void paySuccess(String out_trade_no, Map<String, String> resultMap);

    /**
     * 获取支付记录
     * @param orderId
     * @param paymentType
     * @return
     */
    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);

}
