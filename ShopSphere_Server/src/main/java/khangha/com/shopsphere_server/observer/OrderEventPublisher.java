package khangha.com.shopsphere_server.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderEventPublisher {

    @Autowired
    private List<OrderEventListener> listeners;

    public void publishEvent(OrderEvent event) {
        for (OrderEventListener listener : listeners) {
            if (listener.supportsEventType(event.getEventType())) {
                try {
                    listener.onOrderEvent(event);
                } catch (Exception e) {
                    System.err.println("Error in event listener: " + e.getMessage());
                }
            }
        }
    }
}
