package com.wlsup.yygh.order.service;

import com.wlsup.yygh.model.order.PaymentInfo;
import com.wlsup.yygh.model.order.RefundInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);

}
