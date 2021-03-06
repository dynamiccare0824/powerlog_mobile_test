package test.powerlog.mobile.springboot.domain.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountVwRepository extends JpaRepository<UserAccountVw, String>{
    UserAccountVw findByLoginVwPhone(String phone);
    UserAccountVw findByLoginVwUid(String uid);
}