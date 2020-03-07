package test.powerlog.mobile.springboot.domain.table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="WorkoutTb")
public class WorkoutTb {

    @Id
    private String wIndex;

    @Column
    private String wEmail;

    @Column
    private String wDate;

    @Column
    private String wCommonCode;

    @Column
    private String wWeight;

    @Column
    private String wCount;

    @Column
    private String wSet;

    @Column
    private String wLevel;

    @Column
    private String wRest;

    @Column
    private String wDevice;

//    @Builder
//    public WorkoutTb(String wIndex, String wEmail, String wDate, String wCommonCode, String wWeight, String wCount, int wSet, int wLevel,
//                     String wRest, String wDevice) {
//        this.uEmail = email;
//        this.uPassword = password;
//        this.uUid = uid;
//        this.uName = name;
//        this.uGender = gender;
//        this.uBirth = birth;
//        this.uHeight = height;
//        this.uWeight = weight;
//        this.uAgreeFlag = agreeFlag;
//        this.uPersonalFlag = personalFlag;
//        this.uShapeCode = shapeCode;
//        this.uVerification = verification;
//        this.uCreatedTime = createdTime;
//        this.uUpdatedTime = updatedTime;
//        this.uPhone = phone;
//        this.uCareer = career;
//        this.uQCode = qCode;
//        this.uQAnswer = qAnswer;
//    }
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