package test.powerlog.mobile.springboot.domain.products;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="UserTb")
public class UserTb {


    @Column
    private String uUid;

    @Id
    private String uEmail;

    @Column
    private String uPassword;

    @Column
    private String uName;

    @Column
    private String uGender;

    @Column
    private String uBirth;

    @Column
    private String uPhone;

    @Column
    private int uCareer;

    @Column
    private int uHeight;

    @Column
    private int uWeight;

    @Column
    private String uAgreeFlag;

    @Column
    private String uPersonalFlag;

    @Column
    private String uShapeCode;

    @Column
    private String uVerification;

    @Column
    private Date uCreatedTime;

    @Column
    private Date uUpdatedTime;

    @Column
    private String uQCode;

    @Column
    private String uQAnswer;

    @Builder
    public UserTb(String email, String password, String uid, String name, String gender, String birth, int height, int weight,
                  String agreeFlag, String personalFlag, String shapeCode, String verification, Date createdTime, Date updatedTime, String phone, int career, String qCode, String qAnswer) {
        this.uEmail = email;
        this.uPassword = password;
        this.uUid = uid;
        this.uName = name;
        this.uGender = gender;
        this.uBirth = birth;
        this.uHeight = height;
        this.uWeight = weight;
        this.uAgreeFlag = agreeFlag;
        this.uPersonalFlag = personalFlag;
        this.uShapeCode = shapeCode;
        this.uVerification = verification;
        this.uCreatedTime = createdTime;
        this.uUpdatedTime = updatedTime;
        this.uPhone = phone;
        this.uCareer = career;
        this.uQCode = qCode;
        this.uQAnswer = qAnswer;
    }
}

//    @Id
//    @GeneratedValue // Auto IncId Generator
//    private Long id;
//
//    @Id
//    @Column(length = 20, nullable = false)
//    private String name;
//
//    @Column(columnDefinition = "TEXT", nullable = true)
//    private Integer price;
//    private String memo;
//    private String memo2;
//
//    @Builder
//    public Product(String name, Integer price, String memo,String memo2) {
//        this.name = name;
//        this.price = price;
//        this.memo = memo;
//        this.memo2 = memo2;
//    }

//package test.powerlog.mobile.springboot.domain.products;
//import javax.persistence.*;
//import lombok.*;

//import java.util.Date;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Entity
//@Table(name="test_view")
//public class Product2 {
//    @Id
//    private Long uid;
//
//    @Column
//    @Temporal(TemporalType.DATE)
//    private Date udate;
// }