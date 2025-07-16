package gift.repository;

import gift.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<WishEntity, Long> {
    Optional<WishEntity> findWishesByMemberEntityIdAndProductEntityId(long memberId, long productId);
}