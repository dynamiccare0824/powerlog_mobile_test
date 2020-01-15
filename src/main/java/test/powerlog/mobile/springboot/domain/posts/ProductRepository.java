package test.powerlog.mobile.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, String>{

    @Modifying
    @Transactional
    @Query(value="UPDATE powerlog.product m SET m.name=:#{#product.name}, m.memo=:#{#product.memo}, m.price=:#{#product.price} WHERE m.id = :id", nativeQuery=true)
    void update(@Param("product") Product product, @Param("id") String id);
}