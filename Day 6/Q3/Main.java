package Day6.Q3;

package com.globalpay.checkout;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

interface PaymentProcessor {
    void processPayment(double amount);
}

@Component
@Primary
class StripeProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Stripe Processing Payment : $" + amount);
    }
}

@Component
class SquareProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Square Processing Payment : $" + amount);
    }
}

class BankXmlProcessor implements PaymentProcessor {

    private String endpoint;
    private String apiKey;
    private boolean encryptionEnabled;

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setEncryptionEnabled(boolean encryptionEnabled) {
        this.encryptionEnabled = encryptionEnabled;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Bank XML Processor Payment : $" + amount);
    }
}

class BankXmlProcessorFactoryBean implements FactoryBean<BankXmlProcessor> {

    @Override
    public BankXmlProcessor getObject() {

        BankXmlProcessor processor = new BankXmlProcessor();


        processor.setEndpoint("https://bank-gateway.com");
        processor.setApiKey("BANK_API_KEY");
        processor.setEncryptionEnabled(true);

        System.out.println("BankXmlProcessor Created Through FactoryBean");

        return processor;
    }

    @Override
    public Class<?> getObjectType() {
        return BankXmlProcessor.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}


@Configuration
class PaymentConfig {

    @Bean(name = "bankXmlProcessor")
    public BankXmlProcessorFactoryBean bankXmlProcessorFactoryBean() {
        return new BankXmlProcessorFactoryBean();
    }
}


@Service
class CheckoutService {

    private final PaymentProcessor defaultProcessor;
    private final PaymentProcessor bankProcessor;

    public CheckoutService(
            PaymentProcessor defaultProcessor,
            @Qualifier("bankXmlProcessor") PaymentProcessor bankProcessor) {

        this.defaultProcessor = defaultProcessor;
        this.bankProcessor = bankProcessor;
    }

    public void checkout() {

        System.out.println("Using Default Processor");

        defaultProcessor.processPayment(1000);

        System.out.println("Using Legacy Bank Processor");

        bankProcessor.processPayment(1000);
    }
}
