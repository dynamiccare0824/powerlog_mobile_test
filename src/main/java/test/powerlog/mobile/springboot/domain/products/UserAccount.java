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
@Table(name="m_login_vw")
public class UserAccount {
    @Id
    private String m_login_vw_email;

    @Column
    private String m_login_vw_uid;

    @Column
    private String m_login_vw_password;

    @Column
    private String m_login_vw_name;

    @Column
    private String m_login_vw_gender;

    @Column
    private String m_login_vw_birth;

    @Column
    private String m_login_vw_age;

    @Column
    private String m_login_vw_agecd;

    @Column
    private String m_login_vw_career;
    //n년 m개월로 들어오면 n*12 + m 으로 해서 int x 개월을 넣게 된다

    @Column
    private String m_login_vw_career_now;

    @Column
    private String m_login_vw_phone;

    @Column
    private int m_login_vw_height;

    @Column
    private int m_login_vw_weight;

    @Column
    private String m_login_vw_shape_cd;

    @Column
    private String m_login_vw_certification;

    @Column
    private String m_login_vw_agree_fg;

    @Column
    private String m_login_vw_pd_fg;

    @Column
    private LocalDateTime m_login_vw_createdtime;

    @Column
    private LocalDateTime m_login_vw_updatedtime;

}