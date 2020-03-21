package test.powerlog.mobile.springboot.domain.table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="PlannerByProgramTb")
public class PlannerByProgramTb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plnIndex;

    @Column
    private String plnEmail;

    @Column
    private LocalDate plnStartDate;

    @Column
    private LocalDate plnEndDate;

    @Column
    private LocalDate plnOnDate;

    @Column
    private String plnOnDay;

    @Column
    private String plnChosenDayOfWk;

    @Column
    private String plnCommonCode;

    @Column
    private int plnWeight;

    @Column
    private int plnCount;

    @Column
    private int plnSet;

    @Column
    private int plnLevel;

    @Column
    private int plnRest;

    @Column
    private String plnProgram;

    @Column
    private String plnOnSchedule;

    @Column
    private String plnDone;

    @Column
    private LocalDateTime plnCreated;

    @Column
    private LocalDateTime plnUpdated;



    @Builder
    public PlannerByProgramTb(int index, String email, LocalDate startDate, LocalDate endDate, LocalDate onDate, String onDay, String chosenDayOfWk,
                              String commonCode, int weight, int count, int set, int level, int rest, String program, String done, String onSchedule, LocalDateTime created, LocalDateTime updated){
        this.plnIndex = index;
        this.plnEmail = email;
        this.plnStartDate = startDate;
        this.plnEndDate = endDate;
        this.plnOnDate = onDate;
        this.plnOnDay = onDay;
        this.plnChosenDayOfWk = chosenDayOfWk;
        this.plnCommonCode = commonCode;
        this.plnWeight = weight;
        this.plnCount = count;
        this.plnSet = set;
        this.plnLevel = level;
        this.plnRest = rest;
        this.plnProgram = program;
        this.plnDone = done;
        this.plnOnSchedule = onSchedule;
        this.plnCreated = created;
        this.plnUpdated = updated;
    }
}