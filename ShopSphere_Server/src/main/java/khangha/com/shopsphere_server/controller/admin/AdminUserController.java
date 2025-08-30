package khangha.com.shopsphere_server.controller.admin;

import khangha.com.shopsphere_server.model.entity.Customer;
import khangha.com.shopsphere_server.model.entity.Role;
import khangha.com.shopsphere_server.model.entity.User;
import khangha.com.shopsphere_server.model.entity.UserRole;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

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

    private boolean hasPermissionToCreateRole(String currentUserRole, String targetRole) {
        switch (currentUserRole) {
            case "SUPER_ADMIN":
                return true; // Can create any role
            case "ADMIN":
                return "MANAGER".equals(targetRole) || "STAFF".equals(targetRole);
            case "MANAGER":
                return "STAFF".equals(targetRole);
            default:
                return false;
        }
    }

    private String getCurrentUserRole(String authToken) {
        String token = jwtUtil.extractTokenFromHeader(authToken);
        if (token != null && jwtUtil.validateToken(token)) {
            return jwtUtil.extractRole(token);
        }
        return null;
    }

    private String getCurrentUserId(String authToken) {
        String token = jwtUtil.extractTokenFromHeader(authToken);
        if (token != null && jwtUtil.validateToken(token)) {
            return jwtUtil.extractUserId(token);
        }
        return null;
    }

    private boolean isValidAuthToken(String authToken) {
        String token = jwtUtil.extractTokenFromHeader(authToken);
        return token != null && jwtUtil.validateToken(token);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(
            @RequestHeader("Authorization") String authToken,
            @RequestBody Map<String, String> request) {
        
        Map<String, Object> response = new HashMap<>();

        // Validate auth token
        if (!isValidAuthToken(authToken)) {
            response.put("success", false);
            response.put("message", "Invalid or missing authorization token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        System.out.println("Passed validation");

        // Get current user role and id from token
        String currentUserRole = getCurrentUserRole(authToken);
        String currentUserId = getCurrentUserId(authToken);
        String targetRole = request.get("role");
        
        System.out.println("Current user role: " + currentUserRole);
        System.out.println("Current user id: " + currentUserId);
        System.out.println("Target role: " + targetRole);

        // Check permission
        if (!hasPermissionToCreateRole(currentUserRole, targetRole)) {
            response.put("success", false);
            response.put("message", "Insufficient permissions to create user with role: " + targetRole);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Get current user for created_by reference
        Optional<User> currentUserOpt = userRepository.findById(UUID.fromString(currentUserId));
        if (currentUserOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Current user not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        User currentUser = currentUserOpt.get();

        String email = request.get("email");
        String password = request.get("password");

        if (userRepository.existsByEmail(email)) {
            response.put("success", false);
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        // Create User
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setIsActive(true);
        User savedUser = userRepository.save(user);

        // Create Customer if needed
        if (targetRole.equals("CUSTOMER")) {
            Customer customer = new Customer();
            customer.setUser(savedUser);
            customer.setEmail(email);
            customerRepository.save(customer);
        }

        // Assign Role - Convert to uppercase for consistency
        String normalizedRole = targetRole.toUpperCase();
        Optional<Role> roleOpt = roleRepository.findByName(normalizedRole);
        if (roleOpt.isPresent()) {
            UserRole userRole = new UserRole();
            userRole.setUser(savedUser);
            userRole.setRole(roleOpt.get());
            
            // Set created_by based on role
            if ("SUPER_ADMIN".equals(normalizedRole) || "CUSTOMER".equals(normalizedRole)) {
                userRole.setCreatedBy(savedUser); // Self-created
            } else {
                userRole.setCreatedBy(currentUser); // Created by admin/manager
            }
            
            userRoleRepository.save(userRole);
        } else {
            response.put("success", false);
            response.put("message", "Invalid role: " + targetRole + ". Valid roles are: SUPER_ADMIN, ADMIN, MANAGER, STAFF, CUSTOMER");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", true);
        response.put("message", "User created successfully");
        response.put("userId", savedUser.getId());
        response.put("role", normalizedRole);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestHeader("Authorization") String authToken) {
        
        Map<String, Object> response = new HashMap<>();

        if (!isValidAuthToken(authToken)) {
            response.put("success", false);
            response.put("message", "Invalid or missing authorization token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String currentUserRole = getCurrentUserRole(authToken);
        
        // Only SUPER_ADMIN and ADMIN can view all users
        if (!"SUPER_ADMIN".equals(currentUserRole) && !"ADMIN".equals(currentUserRole)) {
            response.put("success", false);
            response.put("message", "Insufficient permissions to view all users");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        List<User> users = userRepository.findAll();
        
        // Convert to clean DTO format
        List<Map<String, Object>> userDtos = users.stream()
            .map(user -> {
                Map<String, Object> userDto = new HashMap<>();
                userDto.put("id", user.getId());
                userDto.put("email", user.getEmail());
                userDto.put("isActive", user.getIsActive());
                userDto.put("createdAt", user.getCreatedAt());
                userDto.put("updatedAt", user.getUpdatedAt());
                
                // Get user role
                List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
                if (!userRoles.isEmpty()) {
                    userDto.put("role", userRoles.get(0).getRole().getName());
                }
                
                return userDto;
            })
            .collect(java.util.stream.Collectors.toList());
        
        response.put("success", true);
        response.put("users", userDtos);
        response.put("total", users.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUser(
            @RequestHeader("Authorization") String authToken,
            @PathVariable UUID userId) {
        
        Map<String, Object> response = new HashMap<>();

        if (!isValidAuthToken(authToken)) {
            response.put("success", false);
            response.put("message", "Invalid or missing authorization token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String currentUserRole = getCurrentUserRole(authToken);
        
        // Only SUPER_ADMIN, ADMIN, and MANAGER can view user details
        if (!"SUPER_ADMIN".equals(currentUserRole) && 
            !"ADMIN".equals(currentUserRole) && 
            !"MANAGER".equals(currentUserRole)) {
            response.put("success", false);
            response.put("message", "Insufficient permissions to view user details");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        response.put("success", true);
        response.put("user", Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "isActive", user.getIsActive(),
            "createdAt", user.getCreatedAt()
        ));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<Map<String, Object>> updateUserStatus(
            @RequestHeader("Authorization") String authToken,
            @PathVariable UUID userId,
            @RequestBody Map<String, Boolean> request) {
        
        Map<String, Object> response = new HashMap<>();

        if (!isValidAuthToken(authToken)) {
            response.put("success", false);
            response.put("message", "Invalid or missing authorization token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String currentUserRole = getCurrentUserRole(authToken);
        
        // Only SUPER_ADMIN and ADMIN can update user status
        if (!"SUPER_ADMIN".equals(currentUserRole) && !"ADMIN".equals(currentUserRole)) {
            response.put("success", false);
            response.put("message", "Insufficient permissions to update user status");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        Boolean isActive = request.get("isActive");
        
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        user.setIsActive(isActive);
        userRepository.save(user);

        response.put("success", true);
        response.put("message", "User status updated successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @RequestHeader("Authorization") String authToken,
            @PathVariable UUID userId) {
        
        Map<String, Object> response = new HashMap<>();

        if (!isValidAuthToken(authToken)) {
            response.put("success", false);
            response.put("message", "Invalid or missing authorization token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String currentUserRole = getCurrentUserRole(authToken);
        
        // Only SUPER_ADMIN can delete users
        if (!"SUPER_ADMIN".equals(currentUserRole)) {
            response.put("success", false);
            response.put("message", "Only SUPER_ADMIN can delete users");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        if (!userRepository.existsById(userId)) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(userId);

        response.put("success", true);
        response.put("message", "User deleted successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/super-admin")
    public ResponseEntity<Map<String, Object>> createSuperAdmin(
            @RequestHeader("Authorization") String authToken,
            @RequestBody Map<String, String> request) {
        
        Map<String, Object> response = new HashMap<>();

        // Validate auth token
        if (!isValidAuthToken(authToken)) {
            response.put("success", false);
            response.put("message", "Invalid or missing authorization token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Check if current user is SUPER_ADMIN
        String currentUserRole = getCurrentUserRole(authToken);
        if (!"SUPER_ADMIN".equals(currentUserRole)) {
            response.put("success", false);
            response.put("message", "Only SUPER_ADMIN can create another SUPER_ADMIN");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Validate secret key
        String secretKey = request.get("secretKey");
        String expectedSecretKey = "secret";
        
        if (secretKey == null || !expectedSecretKey.equals(secretKey)) {
            response.put("success", false);
            response.put("message", "Invalid secret key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        String email = request.get("email");
        String password = request.get("password");

        if (email == null || password == null) {
            response.put("success", false);
            response.put("message", "Email and password are required");
            return ResponseEntity.badRequest().body(response);
        }

        if (userRepository.existsByEmail(email)) {
            response.put("success", false);
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        // Create User
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setIsActive(true);
        User savedUser = userRepository.save(user);

        // Assign SUPER_ADMIN role (ID = 1)
        Optional<Role> superAdminRoleOpt = roleRepository.findById(1L);
        if (superAdminRoleOpt.isPresent()) {
            UserRole userRole = new UserRole();
            userRole.setUser(savedUser);
            userRole.setRole(superAdminRoleOpt.get());
            userRole.setCreatedBy(savedUser); // Self-created
            userRoleRepository.save(userRole);
        }

        response.put("success", true);
        response.put("message", "Super Admin created successfully");
        response.put("role", "SUPER_ADMIN");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
} 