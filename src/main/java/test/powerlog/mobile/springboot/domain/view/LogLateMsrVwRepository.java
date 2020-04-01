package test.powerlog.mobile.springboot.domain.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogLateMsrVwRepository extends JpaRepository<LogLateMsrVw, String>, JpaSpecificationExecutor<LogLateMsrVw> {
//    EntityManager em = emf.createEntityManager();
    List<LogLateMsrVw> findAllByLgLateMsrVwEmail(String email);
    List<LogLateMsrVw> findAllByLgLateMsrVwCommonCodeOrderByLgLateMsrVwMaxDesc(String commonCode);
//    TypedQuery<LogLateMsrVw> query = em.createQuery
}
