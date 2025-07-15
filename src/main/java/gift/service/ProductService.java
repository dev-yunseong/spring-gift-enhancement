package gift.service;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.dto.ProductStatusPatchRequestDto;
import gift.domain.Product;
import gift.entity.ProductEntity;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long saveProduct(ProductRequestDto productRequestDto) {
        Product product = productRequestDto.toDomain();
        ProductEntity productEntity = new ProductEntity(product);
        productEntity = productRepository.save(productEntity);

        return productEntity.getId();
    }

    public void deleteProductById(Long id) {
        productRepository.deleteProductById(id);
    }

    public void updateProduct(Long id, ProductRequestDto productRequestDto) {
        ProductEntity productEntity = productRepository.findProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

        Product product = productRequestDto.toDomain();
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setImageUrl(product.getImageUrl());
        productEntity.setStatus(product.getStatus());
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findApprovedProducts() {
        return productRepository.findAll().stream()
                .map(ProductEntity::toDomain)
                .filter(Product::isApproved)
                .map(ProductResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductEntity::toDomain)
                .map(ProductResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findProductById(Long id) {
        return productRepository.findProductById(id)
                .map(ProductEntity::toDomain)
                .map(ProductResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));
    }

    public void updateProductStatus(Long productId, ProductStatusPatchRequestDto statusPatchRequestDto) {
        ProductEntity productEntity = productRepository.findProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

        productEntity.setStatus(statusPatchRequestDto.status());
    }
}
