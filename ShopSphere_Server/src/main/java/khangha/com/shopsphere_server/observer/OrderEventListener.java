package khangha.com.shopsphere_server.observer;

public interface OrderEventListener {
    void onOrderEvent(OrderEvent event);
    
    boolean supportsEventType(OrderEvent.OrderEventType eventType);
}
