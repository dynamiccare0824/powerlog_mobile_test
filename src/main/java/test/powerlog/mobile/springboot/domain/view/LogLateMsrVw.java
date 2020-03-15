package test.powerlog.mobile.springboot.domain.view;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="LogLateMsrVw")
public class LogLateMsrVw {

    @Id
    private String lgLateMsrVwIndex;

    @Column
    private String lgLateMsrVwEmail;

    @Column
    private LocalDateTime lgLateMsrVwDateTime;

    @Column
    private String lgLateMsrVwCommonCode;

    @Column
    private int lgLateMsrVwMax;

    @Column
    private String lgLateMsrVwXy;

    @Column
    private String lgLateMsrVwName;
}