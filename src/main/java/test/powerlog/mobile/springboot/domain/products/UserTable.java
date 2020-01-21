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
@Table(name="user_tb")
public class UserTable {
    @Id
    private String u_email;

    @Column
    private String u_uid;

    @Column
    private String u_password;

    @Column
    private String u_name;

    @Column
    private String u_gender;

    @Column
    private String u_birth;

    @Column
    private int u_height;

    @Column
    private int u_weight;

    @Column
    private String u_agree_fg;

    @Column
    private String u_pd_fg;

    @Column
    private String u_goal_cd;

    @Column
    private String u_certification;

    @Column
    private String u_login_fg;

    @Column
    private Date u_createdtime;

    @Column
    private Date u_updatedtime;

    @Builder
    public UserTable(String email, String password, String uid, String name, String gender, String birth, int height, int weight,
                     String agree_fg, String pd_fg, String goal_cd, String certification, String login_fg, Date createdtime, Date updatedtime) {
        this.u_email = email;
        this.u_password = password;
        this.u_uid = uid;
        this.u_name = name;
        this.u_gender = gender;
        this.u_birth = birth;
        this.u_height = height;
        this.u_weight = weight;
        this.u_agree_fg = agree_fg;
        this.u_pd_fg = pd_fg;
        this.u_goal_cd = goal_cd;
        this.u_certification = certification;
        this.u_login_fg = login_fg;
        this.u_createdtime = createdtime;
        this.u_updatedtime = updatedtime;
    }
}
