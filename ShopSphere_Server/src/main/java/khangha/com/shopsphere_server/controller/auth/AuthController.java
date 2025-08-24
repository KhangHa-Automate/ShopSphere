package khangha.com.shopsphere_server.controller.auth;

import khangha.com.shopsphere_server.model.entity.Customer;
import khangha.com.shopsphere_server.model.entity.Role;
import khangha.com.shopsphere_server.model.entity.User;
import khangha.com.shopsphere_server.model.entity.UserRole;
import khangha.com.shopsphere_server.model.request.LoginRequest;
import khangha.com.shopsphere_server.model.request.RegisterRequest;
import khangha.com.shopsphere_server.repository.CustomerRepository;
import khangha.com.shopsphere_server.repository.RoleRepository;
import khangha.com.shopsphere_server.repository.UserRepository;
import khangha.com.shopsphere_server.repository.UserRoleRepository;
import khangha.com.shopsphere_server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (userRepository.existsByEmail(email)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setIsActive(true);

        User savedUser = userRepository.save(user);

        // Create Customer
        Customer customer = new Customer();
        customer.setUser(savedUser);
        customer.setEmail(email);
        customer.setFullName(email.split("@")[0]); // Use email prefix as full name
        customerRepository.save(customer);

        // Assign CUSTOMER role (ID = 5)
        Optional<Role> customerRoleOpt = roleRepository.findById(5L);
        if (customerRoleOpt.isPresent()) {
            UserRole userRole = new UserRole();
            userRole.setUser(savedUser);
            userRole.setRole(customerRoleOpt.get());
            userRole.setCreatedBy(savedUser); // Customer tự tạo role cho chính mình
            userRoleRepository.save(userRole);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "User registered successfully");
        response.put("role", "CUSTOMER");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        Optional<User> userOpt = userRepository.findByEmailAndIsActiveTrue(email);
        
        if (userOpt.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }

        User user = userOpt.get();
        
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }

        // Get user role
        String userRole = "CUSTOMER"; // Default role
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        if (!userRoles.isEmpty()) {
            userRole = userRoles.get(0).getRole().getName();
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), userRole, user.getId().toString());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Login successful");
        response.put("userId", user.getId());
        response.put("token", token);
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader("Authorization") String authToken) {
        Map<String, Object> response = new HashMap<>();
        
        String token = jwtUtil.extractTokenFromHeader(authToken);
        if (token == null) {
            response.put("success", false);
            response.put("message", "Invalid authorization header");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (!jwtUtil.validateToken(token)) {
            response.put("success", false);
            response.put("message", "Invalid or expired token");
            return ResponseEntity.badRequest().body(response);
        }
        
        response.put("success", true);
        response.put("message", "Logout successful");
        
        return ResponseEntity.ok(response);
    }
}
