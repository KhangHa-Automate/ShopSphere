package khangha.com.shopsphere_server.controller.customer;

import khangha.com.shopsphere_server.model.entity.Cart;
import khangha.com.shopsphere_server.model.entity.CartItem;
import khangha.com.shopsphere_server.model.entity.Customer;
import khangha.com.shopsphere_server.model.entity.ProductVariant;
import khangha.com.shopsphere_server.enums.CartStatus;
import khangha.com.shopsphere_server.repository.CartItemRepository;
import khangha.com.shopsphere_server.repository.CartRepository;
import khangha.com.shopsphere_server.repository.CustomerRepository;
import khangha.com.shopsphere_server.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductVariantRepository variantRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCart(@RequestParam UUID customerId) {
        Optional<Cart> cartOpt = cartRepository.findByCustomerIdAndStatus(customerId, CartStatus.OPEN);
        
        if (cartOpt.isEmpty()) {
            return ResponseEntity.ok(Map.of("items", List.of(), "total", 0));
        }
        
        Cart cart = cartOpt.get();
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        
        BigDecimal total = items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        Map<String, Object> response = new HashMap<>();
        response.put("cartId", cart.getId());
        response.put("items", items);
        response.put("total", total);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody Map<String, Object> request) {
        UUID customerId = UUID.fromString(request.get("customerId").toString());
        Long variantId = Long.valueOf(request.get("variantId").toString());
        Integer qty = Integer.valueOf(request.get("qty").toString());
        
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Customer not found"));
        }
        
        Optional<ProductVariant> variantOpt = variantRepository.findById(variantId);
        if (variantOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Product variant not found"));
        }
        
        ProductVariant variant = variantOpt.get();
        
        Optional<Cart> cartOpt = cartRepository.findByCustomerIdAndStatus(customerId, CartStatus.OPEN);
        Cart cart;
        
        if (cartOpt.isEmpty()) {
            cart = new Cart();
            cart.setCustomer(customerOpt.get());
            cart.setStatus(CartStatus.OPEN);
            cart.setCurrency("USD");
            cart = cartRepository.save(cart);
        } else {
            cart = cartOpt.get();
        }
        
        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variantId);
        
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQty(existingItem.getQty() + qty);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setVariant(variant);
            newItem.setQty(qty);
            newItem.setUnitPrice(BigDecimal.valueOf(100.00));
            cartItemRepository.save(newItem);
        }
        
        return ResponseEntity.ok(Map.of("success", true, "message", "Item added to cart"));
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateCartItem(@RequestBody Map<String, Object> request) {
        Long cartItemId = Long.valueOf(request.get("cartItemId").toString());
        Integer qty = Integer.valueOf(request.get("qty").toString());
        
        Optional<CartItem> itemOpt = cartItemRepository.findById(cartItemId);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cart item not found"));
        }
        
        CartItem item = itemOpt.get();
        item.setQty(qty);
        cartItemRepository.save(item);
        
        return ResponseEntity.ok(Map.of("success", true, "message", "Cart item updated"));
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Map<String, Object>> removeFromCart(@PathVariable Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cart item not found"));
        }
        
        cartItemRepository.deleteById(cartItemId);
        
        return ResponseEntity.ok(Map.of("success", true, "message", "Item removed from cart"));
    }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<Map<String, Object>> clearCart(@PathVariable Long cartId) {
        cartItemRepository.deleteByCartId(cartId);
        
        return ResponseEntity.ok(Map.of("success", true, "message", "Cart cleared"));
    }
}
