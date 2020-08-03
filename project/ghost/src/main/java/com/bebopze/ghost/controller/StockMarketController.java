package com.bebopze.ghost.controller;

import com.bebopze.framework.common.model.response.ResultBean;
import com.bebopze.ghost.model.param.MonitorParam;
import com.bebopze.ghost.service.StockMarketService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author bebopze
 * @date 2020/7/24
 */
@Api(tags = "股市")
@RestController
@RequestMapping("/api/v1/stockMarket")
public class StockMarketController {

    @Autowired
    private StockMarketService stockMarketService;


    /**
     * 股价 监听
     *
     * @param param
     * @return
     */
    @PostMapping("/monitor/stockPrice")
    public ResultBean monitorStockPrice(@RequestBody MonitorParam param) {
        return ResultBean.ofSuccess(stockMarketService.monitorStockPrice(param));
    }


    /**
     * 北上资金 监听
     *
     * @param param
     * @return
     */
    @PostMapping("/monitor/northwardFund")
    public ResultBean monitorNorthwardFund(@RequestBody MonitorParam param) {
        return ResultBean.ofSuccess(stockMarketService.monitorNorthwardFund(param));
    }

}
