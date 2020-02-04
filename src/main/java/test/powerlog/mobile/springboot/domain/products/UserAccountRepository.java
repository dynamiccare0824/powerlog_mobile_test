package test.powerlog.mobile.springboot.domain.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String>{

//    @Modifying
//    @Transactional
//    @Query(value="UPDATE powerlog.product m SET m.name=:#{#product.name}, m.memo=:#{#product.memo}, m.price=:#{#product.price} WHERE m.id = :id", nativeQuery=true)
//    void update(@Param("product") TestProduct product, @Param("id") String id);
}