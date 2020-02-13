package test.powerlog.mobile.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserAccountDto {

    private String uid;
    private String email;
    private String password;
    private String name;
    private String gender;
    private String birth;
    private String phone;
    private int career;
    private int height;
    private int weight;
    private String shapeCode;
    private String verification;
    private String agreeFlag;
    private String personalFlag;
    private LocalDateTime createdtime;
    private LocalDateTime updatedtime;
    //    private String age;
//    private String ageCode;

    //n년 m개월로 들어오면 n*12 + m 으로 해서 int x 개월을 넣게 된다
//    private int career_now;

    @Builder
    public UserAccountDto(String uid, String email, String password, String name, String gender, String birth,
    String age, String agecd, int career, int career_now, String phone, int height, int weight, String shape_cd, String verification,
                          String agree_fg, String pd_fg, LocalDateTime createdtime, LocalDateTime updatedtime) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
//        this.age = age;
//        this.ageCode = ageCode;
        this.phone = phone;
        this.career = career;
        //n년 m개월로 들어오면 n*12 + m 으로 해서 int x 개월을 넣게 된다
//        this.career_now = career_now;

        this.height = height;
        this.weight = weight;
        this.shapeCode = shape_cd;
        this.verification = verification;
        this.agreeFlag = agree_fg;
        this.personalFlag = pd_fg;
        this.createdtime = createdtime;
        this.updatedtime = updatedtime;
    }
}
