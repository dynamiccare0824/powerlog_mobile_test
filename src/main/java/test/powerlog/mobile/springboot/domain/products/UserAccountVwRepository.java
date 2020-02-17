package test.powerlog.mobile.springboot.domain.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountVwRepository extends JpaRepository<UserAccountVw, String>{
    UserAccountVw findByLoginVwPhone(String phone);
}