package khangha.com.shopsphere_server.strategy.impl;

import khangha.com.shopsphere_server.enums.PaymentIntentStatus;
import khangha.com.shopsphere_server.enums.PaymentStatus;
import khangha.com.shopsphere_server.model.entity.Payment;
import khangha.com.shopsphere_server.model.entity.PaymentIntent;
import khangha.com.shopsphere_server.strategy.PaymentStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class StripePaymentStrategy implements PaymentStrategy {

    @Override
    public String getProviderName() {
        return "stripe";
    }

    @Override
    public PaymentIntent createPaymentIntent(Long orderId, BigDecimal amount, String currency) {
        PaymentIntent paymentIntent = new PaymentIntent();
        paymentIntent.setOrder(null);
        paymentIntent.setProvider(getProviderName());
        paymentIntent.setAmount(amount);
        paymentIntent.setCurrency(currency);
        paymentIntent.setStatus(PaymentIntentStatus.REQUIRES_ACTION);
        paymentIntent.setIdempotencyKey(UUID.randomUUID().toString());
        paymentIntent.setCreatedAt(OffsetDateTime.now());
        paymentIntent.setUpdatedAt(OffsetDateTime.now());
        
        return paymentIntent;
    }

    @Override
    public Payment processPayment(PaymentIntent paymentIntent, String paymentMethodId) {
        Payment payment = new Payment();
        payment.setOrder(null);
        payment.setProvider(getProviderName());
        payment.setProviderRef("stripe_pi_" + UUID.randomUUID().toString());
        payment.setAmount(paymentIntent.getAmount());
        payment.setStatus(PaymentStatus.SUCCEEDED);
        payment.setPaidAt(OffsetDateTime.now());
        
        return payment;
    }

    @Override
    public boolean refundPayment(Payment payment, BigDecimal amount, String reason) {
        return true;
    }

    @Override
    public boolean isSupported(String paymentMethod) {
        return "card".equals(paymentMethod) || "bank_transfer".equals(paymentMethod);
    }
}
