package test.powerlog.mobile.springboot.domain.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogLateMsrVwRepository extends JpaRepository<LogLateMsrVw, String>, JpaSpecificationExecutor<LogLateMsrVw> {
    List<LogLateMsrVw> findAllByLgLateMsrVwEmail(String email);
}
