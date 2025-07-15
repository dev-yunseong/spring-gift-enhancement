package gift.service;

import gift.domain.Member;
import gift.dto.ProductResponseDto;
import gift.dto.WishResponseDto;
import gift.entity.MemberEntity;
import gift.entity.ProductEntity;
import gift.entity.WishEntity;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class WishService {

    public final WishRepository wishRepository;
    public final ProductRepository productRepository;
    public final MemberRepository memberRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public void saveWish(long memberId, long productId, int count) {
        if (count == 0){
            return;
        }

        MemberEntity memberEntity = memberRepository.findMemberById(memberId)
                        .orElseThrow(() -> new IllegalArgumentException("Member Not Found"));
        ProductEntity productEntity = productRepository.findProductById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));
        WishEntity wishEntity = new WishEntity(count, memberEntity, productEntity);

        wishRepository.save(wishEntity);
    }

    public void updateWishCount(long memberId, long productId, int count) {
        WishEntity wishEntity = wishRepository.findWishesByMemberEntity_IdAndProductEntity_Id(memberId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Wish Not Found"));

        if (count == 0) {
            wishRepository.delete(wishEntity);
            return;
        }

        wishEntity.setCount(count);
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getWishList(long memberId) {
        MemberEntity memberEntity = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member Not Found"));

        return memberEntity.getWishEntities()
                .stream().map(
                    wish ->
                        new WishResponseDto(
                                wish.getCount(),
                                new ProductResponseDto(
                                        wish.getProductEntity().toDomain()
                                )
                        )
                ).toList();
    }
}
