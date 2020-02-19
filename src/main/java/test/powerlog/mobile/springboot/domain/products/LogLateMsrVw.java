package test.powerlog.mobile.springboot.domain.products;

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
    private String lgLateMsrVwLogDate;

    @Column
    private String lgLateMsrVwCommonCode;

    @Column
    private String lgLateMsrVwMax;

    @Column
    private String lgLateMsrVwXy;
}