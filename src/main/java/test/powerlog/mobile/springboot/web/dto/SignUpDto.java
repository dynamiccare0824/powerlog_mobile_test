package test.powerlog.mobile.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.powerlog.mobile.springboot.domain.products.UserTable;
import java.sql.Date;

@Getter
@NoArgsConstructor
public class SignUpDto {


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
    private Date createdTime;
    private Date updatedTime;
    private String qCode;
    private String qAnswer;


    @Builder
    public SignUpDto(String email, String password, String uid, String name, String gender, String birth, int height, int weight,
                     String agreeFlag, String personalFlag, String shapeCode, String verification, Date createdTime, Date updatedTime, String phone, int career, String qCode, String qAnswer) {
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.agreeFlag = agreeFlag;
        this.personalFlag = personalFlag;
        this.shapeCode = shapeCode;
        this.career = career;
        this.verification = verification;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.phone = phone;
        this.qAnswer = qAnswer;
        this.qCode = qCode;
    }

    public UserTable toEntity(){
        System.out.println("hello " + career + qCode + qAnswer
        );
        return UserTable.builder().email(email).password(password).uid(uid).name(name).gender(gender).birth(birth).height(height).weight(weight).career(career)
                .agreeFlag(agreeFlag).personalFlag(personalFlag).shapeCode(shapeCode).verification(verification).createdTime(createdTime).updatedTime(updatedTime).phone(phone).qCode(qCode).qAnswer(qAnswer).build();
    }
}
