package gift.repository;

import gift.domain.Product;
import gift.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByStatus(Product.Status status, Pageable pageable);
    Page<ProductEntity> findAllByStatus(Product.Status status, Pageable pageable);
}