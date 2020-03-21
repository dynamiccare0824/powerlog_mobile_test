package test.powerlog.mobile.springboot.domain.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannerByProgramTbRepository2 extends JpaRepository<PlannerByProgramTb, Integer>{
}