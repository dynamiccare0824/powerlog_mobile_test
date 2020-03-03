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
@Table(name="WorkoutCodeVw")
public class WorkoutCodeVw {

    @Id
    private String wcCommonCode;

    @Column
    private String wcCommonNm;

    @Column
    private String wcCommonMuscle;

    @Column
    private String wcCommonType;
}