
package com.quantifyx.trading;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

interface TradingStrategy {
    void executeTrade();
}

abstract class AbstractStrategy implements TradingStrategy {
    protected String assetClass;
}

@Component
class MomentumStrategy extends AbstractStrategy {

    public MomentumStrategy() {
        this.assetClass = "Equity";
    }

    @Override
    public void executeTrade() {
        System.out.println("Executing Momentum Strategy for " + assetClass);
    }
}

@Component
class ArbitrageStrategy extends AbstractStrategy {

    public ArbitrageStrategy() {
        this.assetClass = "Forex";
    }

    @Override
    public void executeTrade() {
        System.out.println("Executing Arbitrage Strategy for " + assetClass);
    }
}

@Service
class MarketDataService {

    public void loadMarketData() {
        System.out.println("Market Data Loaded");
    }
}

@Service
class AlertService {

    public void sendAlert(String message) {
        System.out.println("ALERT : " + message);
    }
}

@Component
class TradingEngine implements BeanNameAware, InitializingBean {

    private final MarketDataService marketDataService;
    private final List<TradingStrategy> strategies;

    private AlertService alertService;

    private String beanName;

    public TradingEngine(MarketDataService marketDataService,
                         List<TradingStrategy> strategies) {
        this.marketDataService = marketDataService;
        this.strategies = strategies;
    }
    @Autowired(required = false)
    public void setAlertService(AlertService alertService) {
        this.alertService = alertService;
    }


    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("Bean Name : " + beanName);
    }

    @PostConstruct
    public void warmUpCache() {
        System.out.println("Warming up market cache...");
        marketDataService.loadMarketData();
    }

    @Override
    public void afterPropertiesSet() {

        System.out.println("Performing Safety Validation...");

        if (marketDataService == null) {
            throw new IllegalStateException("MarketDataService Missing");
        }

        if (strategies == null || strategies.isEmpty()) {
            throw new IllegalStateException("No Trading Strategies Loaded");
        }

        System.out.println("Validation Successful");
    }

    public void startTrading() {

        System.out.println("Trading Engine Started");

        for (TradingStrategy strategy : strategies) {
            strategy.executeTrade();
        }

        if (alertService != null) {
            alertService.sendAlert("High Risk Trade Monitoring Enabled");
        }
    }

    @PreDestroy
    public void closePositions() {
        System.out.println("Closing all open market positions...");
    }
}
