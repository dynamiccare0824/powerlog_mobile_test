package test.powerlog.mobile.springboot.domain.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogTotalMsrVwRepository extends JpaRepository<LogTotalMsrVw, String>{
    List<LogTotalMsrVw> findAllByLgTotalMsrVwEmail(String email);
    List<LogTotalMsrVw> findAllByLgTotalMsrVwEmailAndLgTotalMsrVwCommonCodeOrderByLgTotalMsrVwDateTimeDesc(String email, String commonCode);
}