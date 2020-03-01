package test.powerlog.mobile.springboot.domain.view;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlannerVwRepository extends JpaRepository<PlannerVw, String> {
    List<PlannerVw> findAllByPlnVwEmail(String email);
}
