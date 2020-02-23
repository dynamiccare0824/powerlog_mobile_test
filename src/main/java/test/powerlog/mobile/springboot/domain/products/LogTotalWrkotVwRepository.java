package test.powerlog.mobile.springboot.domain.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogTotalWrkotVwRepository extends JpaRepository<LogTotalWrkotVw, String>{
    List<LogTotalWrkotVw> findAllByLgTotalWrkotVwEmail(String email);
}