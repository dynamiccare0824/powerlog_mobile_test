package test.powerlog.mobile.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
    private int career_year;
    private int career_month;
    private int height;
    private int weight;
    private String shapeCode;
    private String verification;
    private String agreeFlag;
    private String personalFlag;
    private String questionCode;
    private String questionAnswer;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;


    @Builder
    public UserAccountDto(String uid, String email, String password, String name, String gender, String birth,
                          int career_year, int career_month, String phone, int height, int weight, String shape_cd, String verification,
                          String agree_fg, String pd_fg, LocalDateTime createdTime, LocalDateTime updatedTime, String questionCode, String questionAnswer) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.career_year = career_year;
        this.career_month = career_month;
        this.height = height;
        this.weight = weight;
        this.shapeCode = shape_cd;
        this.verification = verification;
        this.agreeFlag = agree_fg;
        this.personalFlag = pd_fg;
        this.questionAnswer = questionAnswer;
        this.questionCode = questionCode;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
