package test.powerlog.mobile.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.powerlog.mobile.springboot.domain.products.UserTable;
import java.sql.Date;

@Getter
@NoArgsConstructor
public class SignUpDto {

    private String u_email;
    private String u_uid;
    private String u_password;
    private String u_name;
    private String u_gender;
    private String u_birth;
    private int u_height;
    private int u_weight;
    private String u_agree_fg;
    private String u_pd_fg;
    private String u_goal_cd;
    private String u_certification;
    private String u_login_fg;
    private Date u_createdtime;
    private Date u_updatedtime;
    private String u_cellphone;

    @Builder
    public SignUpDto(String email, String password, String uid, String name, String gender, String birth, int height, int weight,
                     String agree_fg, String pd_fg, String goal_cd, String certification, String login_fg, Date createdtime, Date updatedtime, String cellphone) {
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
        this.u_cellphone = cellphone;
    }

    public UserTable toEntity(){
        return UserTable.builder().email(u_email).password(u_password).uid(u_uid).name(u_name).gender(u_gender).birth(u_birth).height(u_height).weight(u_weight)
                .agree_fg(u_agree_fg).pd_fg(u_pd_fg).goal_cd(u_goal_cd).certification(u_certification).login_fg(u_login_fg).createdtime(u_createdtime).updatedtime(u_updatedtime).cellphone(u_cellphone).build();
    }
}
