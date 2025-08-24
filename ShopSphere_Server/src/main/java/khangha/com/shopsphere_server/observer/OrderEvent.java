package khangha.com.shopsphere_server.observer;

import khangha.com.shopsphere_server.model.entity.Order;

import java.time.OffsetDateTime;

public class OrderEvent {
    private final Order order;
    private final OrderEventType eventType;
    private final OffsetDateTime timestamp;

    public OrderEvent(Order order, OrderEventType eventType) {
        this.order = order;
        this.eventType = eventType;
        this.timestamp = OffsetDateTime.now();
    }

    public Order getOrder() {
        return order;
    }

    public OrderEventType getEventType() {
        return eventType;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public enum OrderEventType {
        CREATED,
        PAID,
        SHIPPED,
        DELIVERED,
        CANCELLED,
        REFUNDED
    }
}
