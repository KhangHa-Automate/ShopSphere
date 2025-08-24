package khangha.com.shopsphere_server.service;

import khangha.com.shopsphere_server.dto.ProductDto;
import khangha.com.shopsphere_server.model.request.CreateProductRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> getAllProducts();
    
    List<ProductDto> getActiveProducts();
    
    Optional<ProductDto> getProductBySlug(String slug);
    
    List<ProductDto> getProductsByCategory(Long categoryId);
    
    List<ProductDto> searchProducts(String keyword);
    
    ProductDto createProduct(CreateProductRequest request);
    
    ProductDto updateProduct(Long id, CreateProductRequest request);
    
    void deleteProduct(Long id);
    
    boolean existsBySlug(String slug);
}
