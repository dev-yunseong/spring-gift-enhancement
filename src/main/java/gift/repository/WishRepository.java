package gift.repository;

import gift.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {
    Optional<WishEntity> findWishesByMemberEntity_IdAndProductEntity_Id(long memberId, long productId);
}