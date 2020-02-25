package test.powerlog.mobile.springboot.web.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "", description = "Account 관련 request에서 자주 쓰이는 파라미터값을 모아만든 객체")
public class AccountRequestDto {
    @ApiModelProperty(value = "이메일 아이디", required = true, position = 1, example = "test@gmail.com")
    private String email;
    @ApiModelProperty(value = "비밀번호", required = true, position = 2, example = "test@gmail.com")
    private String password;
    @ApiModelProperty(value = "핸드폰 번호", required = true, position = 3, example = "test@gmail.com")
    private String phone;
    @ApiModelProperty(value = "선택한 질문의 코드", required = true, position = 4, example = "test@gmail.com")
    private String questionCode;
    @ApiModelProperty(value = "선택한 질문에 대한 답변", required = true, position = 5, example = "test@gmail.com")
    private String questionAnswer;
    @ApiModelProperty(value = "운동 목적 코드", required = true, position = 6, example = "test@gmail.com")
    private String shapeCode;
}
