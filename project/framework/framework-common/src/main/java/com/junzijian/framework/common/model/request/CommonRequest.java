package com.junzijian.framework.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 公共参数-订单相关
 *
 * @author liuzhe
 * @date 2019/10/24
 */
@Data
public class CommonRequest implements Serializable {

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * 请求时间戳(单位：秒)
     */
    private int reqTimestamp;

    /**
     * 合伙人ID
     */
    private String partnerId;

    /**
     * 业务员ID
     */
    private String salesmanId;

    /**
     * 订单号
     */
    private String orderId;
}
