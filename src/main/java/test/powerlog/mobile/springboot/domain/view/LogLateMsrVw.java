package test.powerlog.mobile.springboot.domain.view;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String lgLateMsrVwDateTime;

    @Column
    private String lgLateMsrVwCommonCode;

    @Column
    private String lgLateMsrVwMax;

    @Column
    private String lgLateMsrVwXy;

    @Column
    private String lgLateMsrVwName;
}