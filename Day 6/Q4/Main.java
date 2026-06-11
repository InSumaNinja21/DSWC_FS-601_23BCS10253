package Day6.Q4;

package com.securebank.audit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
interface PIIProcessor {
}

@Service
class TransactionService implements PIIProcessor {

    public void processTransaction() {
        System.out.println("Processing Sensitive Transaction");
    }
}

@Service
class PublicCurrencyService {

    public void getExchangeRate() {
        System.out.println("Fetching Public Exchange Rate");
    }
}

@Component
class ComplianceAuditBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(
            Object bean,
            String beanName) throws BeansException {

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(
            Object bean,
            String beanName) throws BeansException {

        if (bean instanceof PIIProcessor) {

            System.out.println(
                    "[AUDIT] Securely Initialized PII Bean : "
                            + beanName
            );
        }

        return bean;
    }
}
