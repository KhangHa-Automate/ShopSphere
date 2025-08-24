package khangha.com.shopsphere_server.controller.customer;

import khangha.com.shopsphere_server.dto.ProductDto;
import khangha.com.shopsphere_server.model.entity.Category;
import khangha.com.shopsphere_server.model.entity.Product;
import khangha.com.shopsphere_server.repository.CategoryRepository;
import khangha.com.shopsphere_server.repository.ProductRepository;
import khangha.com.shopsphere_server.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productRepository.findByIsActiveTrue();
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return ResponseUtil.success(productDtos);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProductDto> getProductBySlug(@PathVariable String slug) {
        Optional<Product> productOpt = productRepository.findBySlug(slug);
        
        if (productOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        ProductDto productDto = convertToDto(productOpt.get());
        return ResponseUtil.success(productDto);
    }

    @GetMapping("/category/{categorySlug}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String categorySlug) {
        Optional<Category> categoryOpt = categoryRepository.findBySlug(categorySlug);
        
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<Product> products = productRepository.findByCategoryIdAndIsActiveTrue(categoryOpt.get().getId());
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return ResponseUtil.success(productDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productRepository.searchByKeyword(keyword);
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return ResponseUtil.success(productDtos);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSlug(product.getSlug());
        dto.setDescription(product.getDescription());
        dto.setAttrs(product.getAttrs());
        dto.setIsActive(product.getIsActive());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        
        return dto;
    }
}
