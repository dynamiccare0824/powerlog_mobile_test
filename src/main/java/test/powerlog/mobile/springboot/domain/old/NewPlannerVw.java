package test.powerlog.mobile.springboot.domain.old;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="NewPlannerVw")
public class NewPlannerVw {

    @Id
    private String plnVwId;

    @Column
    private String plnVwEmail;

    @Column
    private LocalDate plnVwStartDate;

    @Column
    private LocalDate plnVwEndDate;

    @Column
    private LocalDate plnVwOnDate;

    @Column
    private String plnVwOnDay;

    @Column
    private String plnVwCommonCode;

    @Column
    private String plnVwChosenDayOfWk;

    @Column
    private int plnVwWeight;

    @Column
    private int plnVwCount;

    @Column
    private int plnVwSet;

    @Column
    private int plnVwLevel;

    @Column
    private int plnVwRest;

    @Column
    private String plnVwIsProgram;

    @Column
    private String plnVwOnSchedule;

    @Column
    private String plnVwIsDone;

    @Column
    private LocalDateTime plnVwCreatedTime;

    @Column
    private LocalDateTime plnVwUpdatedTime;
}