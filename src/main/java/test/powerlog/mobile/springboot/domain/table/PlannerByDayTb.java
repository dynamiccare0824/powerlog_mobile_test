package test.powerlog.mobile.springboot.domain.table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="PlannerByDayTb")
public class PlannerByDayTb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pdIndex;

    @Column
    private String pdEmail;

    @Column
    private LocalDate pdDate;

    @Column
    private String pdDayOfWk;

    @Column
    private String pdCommonCode;

    @Column
    private int pdWeight;

    @Column
    private int pdCount;

    @Column
    private int pdSet;

    @Column
    private int pdLevel;

    @Column
    private int pdRest;

    @Column
    private String pdProgram;

    @Column
    private String pdOnSchedule;

    @Column
    private String pdDone;

    @Column
    private LocalDateTime pdCreated;

    @Column
    private LocalDateTime pdUpdated;



    @Builder
    public PlannerByDayTb(String index, String email, LocalDate date, String dayOfWk,
                          String commonCode, int weight, int count, int set, int level, int rest, String program,
                          String done, String onSchedule, LocalDateTime created, LocalDateTime updated){
        this.pdIndex = index;
        this.pdEmail = email;
        this.pdDate = date;
        this.pdDayOfWk = dayOfWk;
        this.pdCommonCode = commonCode;
        this.pdWeight = weight;
        this.pdCount = count;
        this.pdSet = set;
        this.pdLevel = level;
        this.pdRest = rest;
        this.pdProgram = program;
        this.pdDone = done;
        this.pdOnSchedule = onSchedule;
        this.pdCreated = created;
        this.pdUpdated = updated;
    }
}