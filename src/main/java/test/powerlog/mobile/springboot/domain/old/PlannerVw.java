package test.powerlog.mobile.springboot.domain.old;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="PlannerVw")
public class PlannerVw {

    @Id
    private String plnVwConcat;

    @Column
    private String plnVwEmail;

    @Column
    private String plnVwOnDate;

    @Column
    private String plnVwCommonCode;

    @Column
    private String plnVwWeight;

    @Column
    private String plnVwCount;

    @Column
    private String plnVwSet;

    @Column
    private String plnVwLevel;

    @Column
    private String plnVwRest;

    @Column
    private String plnVwProgram;

    @Column
    private String plnVwFlag;





}