package khangha.com.shopsphere_server.strategy;

import khangha.com.shopsphere_server.model.entity.Payment;
import khangha.com.shopsphere_server.model.entity.PaymentIntent;

import java.math.BigDecimal;

public interface PaymentStrategy {
    String getProviderName();
    
    PaymentIntent createPaymentIntent(Long orderId, BigDecimal amount, String currency);
    
    Payment processPayment(PaymentIntent paymentIntent, String paymentMethodId);
    
    boolean refundPayment(Payment payment, BigDecimal amount, String reason);
    
    boolean isSupported(String paymentMethod);
}
