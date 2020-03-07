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
@Table(name="LogTotalWrkotVw")
public class LogTotalWrkotVw {

    @Id
    private String lgTotalWrkotVwIndex;

    @Column
    private String lgTotalWrkotVwEmail;

    @Column
    private String lgTotalWrkotVwDateTime;

    @Column
    private String lgTotalWrkotVwCommonCode;

    @Column
    private String lgTotalWrkotVwWeight;

    @Column
    private String lgTotalWrkotVwCount;

    @Column
    private String lgTotalWrkotVwSet;

    @Column
    private String lgTotalWrkotVwLevel;

    @Column
    private String lgTotalWrkotVwRest;

    @Column
    private String lgTotalWrkotVwDevice;
}