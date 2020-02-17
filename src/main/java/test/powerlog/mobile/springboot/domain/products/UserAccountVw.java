package test.powerlog.mobile.springboot.domain.products;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="LoginVw")
public class UserAccountVw {
    @Id
    private String loginVwEmail;

    @Column
    private String loginVwUid;

    @Column
    private String loginVwPassword;

    @Column
    private String loginVwName;

    @Column
    private String loginVwGender;

    @Column
    private String loginVwBirth;

    @Column
    private String loginVwAge;

    @Column
    private String loginVwAgeCode;

    @Column
    private String loginVwCareer;
    //n년 m개월로 들어오면 n*12 + m 으로 해서 int x 개월을 넣게 된다

    @Column
    private String loginVwCareerNow;

    @Column
    private String loginVwPhone;

    @Column
    private int loginVwHeight;

    @Column
    private int loginVwWeight;

    @Column
    private String loginVwShapeCode;

    @Column
    private String loginVwVerification;

    @Column
    private String loginVwAgreeFlag;

    @Column
    private String loginVwPersonalFlag;

    @Column
    private LocalDateTime loginVwCreatedTime;

    @Column
    private LocalDateTime loginVwUpdatedTime;

}