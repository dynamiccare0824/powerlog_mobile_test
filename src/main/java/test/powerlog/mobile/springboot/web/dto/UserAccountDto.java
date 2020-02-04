package test.powerlog.mobile.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserAccountDto {

    private String m_login_vw_uid;
    private String m_login_vw_email;
    private String m_login_vw_password;
    private String m_login_vw_name;
    private String m_login_vw_gender;
    private String m_login_vw_birth;
    private String m_login_vw_age;
    private String m_login_vw_agecd;
    private int m_login_vw_career;
    //n년 m개월로 들어오면 n*12 + m 으로 해서 int x 개월을 넣게 된다
    private int m_login_vw_career_now;
    private String m_login_vw_phone;
    private int m_login_vw_height;
    private int m_login_vw_weight;
    private String m_login_vw_shape_cd;
    private String m_login_vw_certification;
    private String m_login_vw_agree_fg;
    private String m_login_vw_pd_fg;
    private LocalDateTime m_login_vw_createdtime;
    private LocalDateTime m_login_vw_updatedtime;

    @Builder
    public UserAccountDto(String uid, String email, String password, String name, String gender, String birth,
    String age, String agecd, int career, int career_now, String phone, int height, int weight, String shape_cd, String certification,
                          String agree_fg, String pd_fg, LocalDateTime createdtime, LocalDateTime updatedtime) {
        this.m_login_vw_uid = uid;
        this.m_login_vw_email = email;
        this.m_login_vw_password = password;
        this.m_login_vw_name = name;
        this.m_login_vw_gender = gender;
        this.m_login_vw_birth = birth;
        this.m_login_vw_age = age;
        this.m_login_vw_agecd = agecd;
        this.m_login_vw_career = career;
        //n년 m개월로 들어오면 n*12 + m 으로 해서 int x 개월을 넣게 된다
        this.m_login_vw_career_now = career_now;
        this.m_login_vw_phone = phone;
        this.m_login_vw_height = height;
        this.m_login_vw_weight = weight;
        this. m_login_vw_shape_cd = shape_cd;
        this. m_login_vw_certification = certification;
        this. m_login_vw_agree_fg = agree_fg;
        this. m_login_vw_pd_fg = pd_fg;
        this.m_login_vw_createdtime = createdtime;
        this.m_login_vw_updatedtime = updatedtime;
    }
}
