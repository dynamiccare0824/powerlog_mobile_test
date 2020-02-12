package test.powerlog.mobile.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserAccountDto {

    private String vw_uid;
    private String vw_email;
    private String vw_password;
    private String vw_name;
    private String vw_gender;
    private String vw_birth;
    private String vw_age;
    private String vw_agecd;
    private int vw_career;
    //n년 m개월로 들어오면 n*12 + m 으로 해서 int x 개월을 넣게 된다
    private int vw_career_now;
    private String vw_phone;
    private int vw_height;
    private int vw_weight;
    private String vw_shape_cd;
    private String vw_certification;
    private String vw_agree_fg;
    private String vw_pd_fg;
    private LocalDateTime vw_createdtime;
    private LocalDateTime vw_updatedtime;

    @Builder
    public UserAccountDto(String uid, String email, String password, String name, String gender, String birth,
    String age, String agecd, int career, int career_now, String phone, int height, int weight, String shape_cd, String certification,
                          String agree_fg, String pd_fg, LocalDateTime createdtime, LocalDateTime updatedtime) {
        this.vw_uid = uid;
        this.vw_email = email;
        this.vw_password = password;
        this.vw_name = name;
        this.vw_gender = gender;
        this.vw_birth = birth;
        this.vw_age = age;
        this.vw_agecd = agecd;
        this.vw_career = career;
        //n년 m개월로 들어오면 n*12 + m 으로 해서 int x 개월을 넣게 된다
        this.vw_career_now = career_now;
        this.vw_phone = phone;
        this.vw_height = height;
        this.vw_weight = weight;
        this.vw_shape_cd = shape_cd;
        this.vw_certification = certification;
        this.vw_agree_fg = agree_fg;
        this.vw_pd_fg = pd_fg;
        this.vw_createdtime = createdtime;
        this.vw_updatedtime = updatedtime;
    }
}
