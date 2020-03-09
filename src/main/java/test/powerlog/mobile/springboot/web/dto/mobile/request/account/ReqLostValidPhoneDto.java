package test.powerlog.mobile.springboot.web.dto.mobile.request.account;

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
@ApiModel(value = "", description = "Account 관련 request에서 자주 쓰이는 파라미터값을 모아만든 객체")
public class ReqLostValidPhoneDto {
    @NotEmpty(message = "정보를 모두 입력하세요.")
    @Email
    @ApiModelProperty(value = "이메일 아이디", required = true, position = 1, example = "test@gmail.com")
    private String email;

    @NotEmpty(message = "정보를 모두 입력하세요.")
    @ApiModelProperty(value = "핸드폰 번호", required = true, position = 2, example = "01050055438")
    private String phone;
}
