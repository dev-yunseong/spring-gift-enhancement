package gift.repository;

import gift.domain.Product;
import gift.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findAllByStatus(Product.Status status, Pageable pageable);

    @EntityGraph(attributePaths = "optionEntities")
    Optional<ProductEntity> findWithOptionsById(long id);
}