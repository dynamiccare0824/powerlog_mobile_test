package test.powerlog.mobile.springboot.domain.old;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlannerVwRepository extends JpaRepository<PlannerVw, String> {
    List<PlannerVw> findAllByPlnVwEmail(String email);

    @Query(value = "select * from PlannerVw b where b.plnVwEmail = :email", nativeQuery = true)
    List<PlannerVw> findSomeCaseQueryNative(@Param("email") String email);
}
