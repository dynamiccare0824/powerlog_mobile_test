package test.powerlog.mobile.springboot.web.dto.mobile.request;

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
@ApiModel(value = "", description = "Account 관련 request에서 자주 쓰이는 파라미터값을 모아만든 객체")
public class ReqDupCheckSendMsgDto {
    @NotEmpty(message = "휴대폰 번호를 공백없이 올바르게 입력해주세요")
    @Size(min = 10, max = 11, message = "휴대폰 번호를 올바르게 입력해주세요")
    @ApiModelProperty(value = "핸드폰 번호", required = true, position = 1, example = "010123456789")
    private String phone;
}
