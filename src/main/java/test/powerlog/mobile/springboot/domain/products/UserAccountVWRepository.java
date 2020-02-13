package test.powerlog.mobile.springboot.domain.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountVWRepository extends JpaRepository<UserAccountVW, String>{
    UserAccountVW findByLoginVwPhone(String phone);
}