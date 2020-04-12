package test.powerlog.mobile.springboot.domain.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlannerByProgramTbRepository extends JpaRepository<PlannerByProgramTb, String>{
    List<PlannerByProgramTb> findAllByPlnEmail(String email);

    List<PlannerByProgramTb> findAllByPlnEmailOrderByPlnOnDateDesc(String email);

    @Transactional
    void deleteAllByPlnEmail(String email);

    @Transactional
    void deleteAllByPlnEmailAndPlnDone(String email, String trueorfalse);
}