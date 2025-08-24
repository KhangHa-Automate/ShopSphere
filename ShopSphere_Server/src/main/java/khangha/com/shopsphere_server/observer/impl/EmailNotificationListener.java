package khangha.com.shopsphere_server.observer.impl;

import khangha.com.shopsphere_server.observer.OrderEvent;
import khangha.com.shopsphere_server.observer.OrderEventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener implements OrderEventListener {

    @Override
    public void onOrderEvent(OrderEvent event) {
        switch (event.getEventType()) {
            case CREATED:
                sendOrderConfirmationEmail(event);
                break;
            case PAID:
                sendPaymentConfirmationEmail(event);
                break;
            case SHIPPED:
                sendShippingNotificationEmail(event);
                break;
            case DELIVERED:
                sendDeliveryConfirmationEmail(event);
                break;
            case CANCELLED:
                sendOrderCancellationEmail(event);
                break;
            case REFUNDED:
                sendRefundNotificationEmail(event);
                break;
        }
    }

    @Override
    public boolean supportsEventType(OrderEvent.OrderEventType eventType) {
        return true;
    }

    private void sendOrderConfirmationEmail(OrderEvent event) {
        System.out.println("Sending order confirmation email for order: " + event.getOrder().getOrderNumber());
    }

    private void sendPaymentConfirmationEmail(OrderEvent event) {
        System.out.println("Sending payment confirmation email for order: " + event.getOrder().getOrderNumber());
    }

    private void sendShippingNotificationEmail(OrderEvent event) {
        System.out.println("Sending shipping notification email for order: " + event.getOrder().getOrderNumber());
    }

    private void sendDeliveryConfirmationEmail(OrderEvent event) {
        System.out.println("Sending delivery confirmation email for order: " + event.getOrder().getOrderNumber());
    }

    private void sendOrderCancellationEmail(OrderEvent event) {
        System.out.println("Sending order cancellation email for order: " + event.getOrder().getOrderNumber());
    }

    private void sendRefundNotificationEmail(OrderEvent event) {
        System.out.println("Sending refund notification email for order: " + event.getOrder().getOrderNumber());
    }
}
