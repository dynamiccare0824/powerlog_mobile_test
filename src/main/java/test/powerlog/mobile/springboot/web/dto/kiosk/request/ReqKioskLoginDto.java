package test.powerlog.mobile.springboot.web.dto.kiosk.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "", description = "키오스크에서 uid를 받아 계정을 특정하기 위한 객체")
public class ReqKioskLoginDto {
    @NotEmpty(message = "휴대폰 번호를 공백없이 올바르게 입력해주세요")
    @Size(min = 12, max = 12, message = "고유번호는 12자리 입니다.")
    @ApiModelProperty(value = "고유 번호", required = true, position = 1, example = "0123456789*#")
    private String uid;
}
