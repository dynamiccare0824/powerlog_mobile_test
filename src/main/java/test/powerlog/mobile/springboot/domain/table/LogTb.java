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
@Table(name="LogTb")
public class LogTb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String lgIndex;

    @Column
    private String lgEmail;

    @Column
    private LocalDateTime lgDateTime;

    @Column
    private LocalDate lgDate;

    @Column
    private String lgCommonCode;

    @Column
    private int lgStart;

    @Column
    private int lgAvg;

    @Column
    private int lgMax;

    @Column
    private int lgMin;

    @Column
    private String lgDevice;

    @Builder
    public LogTb(String lgIndex, String lgEmail, LocalDateTime lgDateTime, String lgCommonCode, int lgStart, int lgAvg, int lgMax, int lgMin,
                 String lgDevice, LocalDate lgDate) {
        this.lgIndex = lgIndex;
        this.lgEmail = lgEmail;
        this.lgDateTime = lgDateTime;
        this.lgCommonCode = lgCommonCode;
        this.lgDate = lgDate;
        this.lgStart = lgStart;
        this.lgAvg = lgAvg;
        this.lgMax = lgMax;
        this.lgMin = lgMin;
        this.lgDevice = lgDevice;

    }
}