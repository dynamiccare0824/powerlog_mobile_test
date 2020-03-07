package test.powerlog.mobile.springboot.domain.view;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="ProgramTypeVw")
public class ProgramTypeVw {

    @Id
    private String prgVwType;

    @Column
    private String prgVwDayOne;

    @Column
    private String prgVwDayTwo;

    @Column
    private String prgVwDayThree;

    @Column
    private String prgVwDayFour;

    @Column
    private String prgVwDayFive;
}