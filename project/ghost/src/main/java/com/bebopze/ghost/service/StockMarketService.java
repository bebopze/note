package com.bebopze.ghost.service;

import com.bebopze.ghost.model.param.MonitorParam;

/**
 * @author bebopze
 * @date 2020/7/24
 */
public interface StockMarketService {

    /**
     * 股价 监听
     *
     * @param param
     * @return
     */
    Object monitorStockPrice(MonitorParam param);

    /**
     * 北上资金 监听
     *
     * @param param
     * @return
     */
    Object monitorNorthwardFund(MonitorParam param);
}
