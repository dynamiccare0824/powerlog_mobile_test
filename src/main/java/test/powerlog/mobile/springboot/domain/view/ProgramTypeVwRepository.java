package test.powerlog.mobile.springboot.domain.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramTypeVwRepository extends JpaRepository<ProgramTypeVw, String>{

}