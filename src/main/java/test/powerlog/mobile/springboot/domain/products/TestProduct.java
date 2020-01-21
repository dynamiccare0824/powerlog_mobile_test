package test.powerlog.mobile.springboot.domain.products;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="login_view")
public class TestProduct {
    @Id
    private String id;

    @Column
    private String password;


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
//
//    private String memo;
//
//    private String memo2;
//
//    @Builder
//    public Product(String name, Integer price, String memo,String memo2) {
//        this.name = name;
//        this.price = price;
//        this.memo = memo;
//        this.memo2 = memo2;
//    }
}

//package test.powerlog.mobile.springboot.domain.products;
//
//import javax.persistence.*;
//
//import lombok.*;
//
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
//}