package test.powerlog.mobile.springboot.domain.table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="WorkoutTb")
public class WorkoutTb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String wIndex;

    @Column
    private String wEmail;

    @Column
    private LocalDateTime wDateTime;

    @Column
    private LocalDate wDate;

    @Column
    private String wCommonCode;

    @Column
    private int wWeight;

    @Column
    private int wCount;

    @Column
    private int wSet;

    @Column
    private int wLevel;

    @Column
    private int wRest;

    @Column
    private String wDevice;

    @Builder
    public WorkoutTb(String wIndex, String wEmail, LocalDateTime wDateTime, String wCommonCode, int wWeight, int wCount, int wSet, int wLevel,
                     int wRest, String wDevice, LocalDate wDate) {
        this.wIndex = wIndex;
        this.wEmail = wEmail;
        this.wDateTime = wDateTime;
        this.wCommonCode = wCommonCode;
        this.wWeight = wWeight;
        this.wCount = wCount;
        this.wSet = wSet;
        this.wLevel = wLevel;
        this.wRest = wRest;
        this.wDevice = wDevice;
        this.wDate = wDate;
    }
}