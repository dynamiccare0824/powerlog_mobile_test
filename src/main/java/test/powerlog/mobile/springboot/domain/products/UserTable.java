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
public class UserTable {
    @Id
    private String uEmail;

    @Column
    private String uUid;

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

    @Builder
    public UserTable(String email, String password, String uid, String name, String gender, String birth, int height, int weight,
                     String agree_fg, String pd_fg, String goal_cd, String certification, String login_fg, Date createdtime, Date updatedtime, String cellphone) {
        this.uEmail = email;
        this.uPassword = password;
        this.uUid = uid;
        this.uName = name;
        this.uGender = gender;
        this.uBirth = birth;
        this.uHeight = height;
        this.uWeight = weight;
        this.uAgreeFlag = agree_fg;
        this.uPersonalFlag = pd_fg;
        this.uShapeCode = goal_cd;
        this.uVerification = certification;
        this.uCreatedTime = createdtime;
        this.uUpdatedTime = updatedtime;
        this.uPhone = cellphone;
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