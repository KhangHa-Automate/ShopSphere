package khangha.com.shopsphere_server.builder;

import khangha.com.shopsphere_server.enums.OrderStatus;
import khangha.com.shopsphere_server.model.entity.Customer;
import khangha.com.shopsphere_server.model.entity.Order;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderBuilder {
    private Order order;

    public OrderBuilder() {
        this.order = new Order();
        this.order.setCreatedAt(OffsetDateTime.now());
        this.order.setStatus(OrderStatus.PENDING);
        this.order.setCurrency("USD");
        this.order.setSubtotalAmount(BigDecimal.ZERO);
        this.order.setTaxAmount(BigDecimal.ZERO);
        this.order.setShippingAmount(BigDecimal.ZERO);
        this.order.setDiscountAmount(BigDecimal.ZERO);
        this.order.setTotalAmount(BigDecimal.ZERO);
    }

    public OrderBuilder withOrderNumber(String orderNumber) {
        this.order.setOrderNumber(orderNumber);
        return this;
    }

    public OrderBuilder withCustomer(Customer customer) {
        this.order.setCustomer(customer);
        return this;
    }

    public OrderBuilder withStatus(OrderStatus status) {
        this.order.setStatus(status);
        return this;
    }

    public OrderBuilder withCurrency(String currency) {
        this.order.setCurrency(currency);
        return this;
    }

    public OrderBuilder withSubtotalAmount(BigDecimal subtotalAmount) {
        this.order.setSubtotalAmount(subtotalAmount);
        return this;
    }

    public OrderBuilder withTaxAmount(BigDecimal taxAmount) {
        this.order.setTaxAmount(taxAmount);
        return this;
    }

    public OrderBuilder withShippingAmount(BigDecimal shippingAmount) {
        this.order.setShippingAmount(shippingAmount);
        return this;
    }

    public OrderBuilder withDiscountAmount(BigDecimal discountAmount) {
        this.order.setDiscountAmount(discountAmount);
        return this;
    }

    public OrderBuilder withTotalAmount(BigDecimal totalAmount) {
        this.order.setTotalAmount(totalAmount);
        return this;
    }

    public OrderBuilder withShippingAddress(String shippingAddress) {
        this.order.setShippingAddress(shippingAddress);
        return this;
    }

    public OrderBuilder withBillingAddress(String billingAddress) {
        this.order.setBillingAddress(billingAddress);
        return this;
    }

    public OrderBuilder withPlacedAt(OffsetDateTime placedAt) {
        this.order.setPlacedAt(placedAt);
        return this;
    }

    public Order build() {
        return this.order;
    }
}
