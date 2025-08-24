package khangha.com.shopsphere_server.factory;

import khangha.com.shopsphere_server.strategy.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentStrategyFactory {

    private final Map<String, PaymentStrategy> strategies;

    @Autowired
    public PaymentStrategyFactory(List<PaymentStrategy> paymentStrategies) {
        this.strategies = paymentStrategies.stream()
                .collect(Collectors.toMap(
                        PaymentStrategy::getProviderName,
                        Function.identity()
                ));
    }

    public PaymentStrategy getStrategy(String providerName) {
        PaymentStrategy strategy = strategies.get(providerName);
        if (strategy == null) {
            throw new IllegalArgumentException("Payment provider not supported: " + providerName);
        }
        return strategy;
    }

    public List<String> getSupportedProviders() {
        return strategies.keySet().stream().collect(Collectors.toList());
    }
}
