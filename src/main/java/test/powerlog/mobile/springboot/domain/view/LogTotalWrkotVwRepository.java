package test.powerlog.mobile.springboot.domain.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogTotalWrkotVwRepository extends JpaRepository<LogTotalWrkotVw, String>{
    List<LogTotalWrkotVw> findAllByLgTotalWrkotVwEmail(String email);
}