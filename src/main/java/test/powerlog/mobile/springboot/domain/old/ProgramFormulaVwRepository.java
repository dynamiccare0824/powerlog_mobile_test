package test.powerlog.mobile.springboot.domain.old;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramFormulaVwRepository extends JpaRepository<ProgramFormulaVw, String>{
}