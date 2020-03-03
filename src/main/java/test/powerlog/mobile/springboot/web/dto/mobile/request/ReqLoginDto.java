package test.powerlog.mobile.springboot.web.dto.mobile.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "", description = "이메일 아이디와 비밀번호를 통해 로그인을 시도하기 위한 객체")
public class ReqLoginDto {
    @NotEmpty(message = "이메일과 비밀번호를 모두 입력해주십시오.")
    @Email(message = "이메일 형식을 맞춰주세요")
    @ApiModelProperty(value = "이메일 아이디", required = true, position = 1, example = "test@gmail.com")
    private String email;

    @NotEmpty(message = "이메일과 비밀번호를 모두 입력해주십시오.")
    @ApiModelProperty(value = "비밀번호", required = true, position = 2, example = "111111")
    private String password;
}
