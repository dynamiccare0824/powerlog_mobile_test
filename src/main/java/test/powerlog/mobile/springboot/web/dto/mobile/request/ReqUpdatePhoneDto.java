package test.powerlog.mobile.springboot.web.dto.mobile.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdatePhoneDto {
    @NotEmpty(message = "이메일을 공백없이 올바르게 입력해주세요")
    @Email
    @ApiModelProperty(value = "이메일", required = true, position = 1, example = "test@gmail.com")
    private String email;
    @NotEmpty(message = "휴대폰 번호를 공백없이 올바르게 입력해주세요")
    @Pattern(regexp="(?=.*[0-9]).{10,11}",
            message = "휴대폰 번호를 공백없이 올바르게 입력해주세요")
    @ApiModelProperty(value = "핸드폰 번호", required = true, position = 2, example = "01099999999")
    private String phone;
}
