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
public class ProgramFormulaVw {

    @Id
    private String prgfVwIndex;

    @Column
    private String prgfVwBodyShape;

    @Column
    private String prgfVwWeightRate;

    @Column
    private String prgfVwCount;

    @Column
    private String prgfVwCSet;

    @Column
    private String prgfVwRest;
}