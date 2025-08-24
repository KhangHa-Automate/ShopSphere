package khangha.com.shopsphere_server.util;

import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    
    public static <T> ResponseEntity<T> success(T data) {
        return ResponseEntity.ok(data);
    }
    
    public static ResponseEntity<String> success(String message) {
        return ResponseEntity.ok(message);
    }
    
    public static ResponseEntity<String> error(String message) {
        return ResponseEntity.badRequest().body(message);
    }
    
    public static ResponseEntity<String> notFound(String message) {
        return ResponseEntity.notFound().build();
    }
}
