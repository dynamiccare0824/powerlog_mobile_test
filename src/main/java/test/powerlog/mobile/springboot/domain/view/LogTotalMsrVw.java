package test.powerlog.mobile.springboot.domain.view;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="LogTotalMsrVw")
public class LogTotalMsrVw {

    @Id
    private String lgTotalMsrVwLogIndex;

    @Column
    private String lgTotalMsrVwEmail;

    @Column
    private String lgTotalMsrVwName;

    @Column
    private String lgTotalMsrVwLogDate;

    @Column
    private String lgTotalMsrVwAge;

    @Column
    private String lgTotalMsrVwAgeCode;

    @Column
    private String lgTotalMsrVwShape;

    @Column
    private String lgTotalMsrVwCareer;

    @Column
    private String lgTotalMsrVwCareerNow;

    @Column
    private String lgTotalMsrVwHeight;

    @Column
    private String lgTotalMsrVwWeight;

    @Column
    private String lgTotalMsrVwCommonCode;

    @Column
    private String lgTotalMsrVwCommonNm;

    @Column
    private String lgTotalMsrVwMuscle;

    @Column
    private String lgTotalMsrVwType;

    @Column
    private String lgTotalMsrVwStart;

    @Column
    private String lgTotalMsrVwMax;

    @Column
    private String lgTotalMsrVwMin;

    @Column
    private String lgTotalMsrVwAvg;

    @Column
    private float lgTotalMsrVwStartPercent;

    @Column
    private float lgTotalMsrVwMaxPercent;

    @Column
    private float lgTotalMsrVwMinPercent;

    @Column
    private float lgTotalMsrVwAvgPercent;

    @Column
    private String lgTotalMsrVwXy;

    @Column
    private String lgTotalMsrVwDevice;
}