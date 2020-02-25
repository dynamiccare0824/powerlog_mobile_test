package test.powerlog.mobile.springboot.web.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@ApiModel
@RequiredArgsConstructor
public class UserAccountDto {

    private String uid;
    private String email;
    private String password;
    private String name;
    private String gender;
    private String birth;
    private String phone;
    private int careerYear;
    private int careerMonth;
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
                          int careerYear, int careerMonth, String phone, int height, int weight, String shape_cd, String verification,
                          String agree_fg, String pd_fg, LocalDateTime createdTime, LocalDateTime updatedTime, String questionCode, String questionAnswer) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.careerYear = careerYear;
        this.careerMonth = careerMonth;
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
