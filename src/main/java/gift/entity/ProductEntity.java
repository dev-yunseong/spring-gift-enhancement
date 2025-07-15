package gift.entity;

import gift.domain.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(nullable = false, length = 15)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    private int price;

    public void setPrice(int price) {
        this.price = price;
    }

    @Column(nullable = false, length = 255)
    private String imageUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(nullable = false)
    private Product.Status status;

    public void setStatus(Product.Status status) {
        this.status = status;
    }

    public Product toDomain() {
        return new Product(id, name, price, imageUrl, status);
    }

    protected ProductEntity() {}

    public ProductEntity(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getStatus());
    }

    public ProductEntity(Long id, String name, int price, String imageUrl, Product.Status status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
    }
}
