package test.powerlog.mobile.springboot.domain.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannerByDayTbRepository2 extends JpaRepository<PlannerByDayTb, Integer>{
}