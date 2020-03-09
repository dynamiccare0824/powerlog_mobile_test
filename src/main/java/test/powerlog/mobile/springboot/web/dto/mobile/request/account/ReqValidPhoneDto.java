package test.powerlog.mobile.springboot.web.dto.mobile.request.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqValidPhoneDto {
    @NotEmpty(message = "휴대폰 번호를 공백없이 올바르게 입력해주세요")
    @Pattern(regexp="(?=.*[0-9]).{10,11}",
            message = "휴대폰 번호를 공백없이 올바르게 입력해주세요")
    @ApiModelProperty(value = "핸드폰 번호", required = true, position = 1, example = "010123456789")
    private String phone;
}
