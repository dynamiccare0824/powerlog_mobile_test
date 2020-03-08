package test.powerlog.mobile.springboot.web.dto.mobile.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "", description = "이메일로 임시 번호 받기")
public class ReqEmailQuestionDto {
    @ApiModelProperty(value = "이메일 아이디", required = true, position = 1, example = "test@gmail.com")
    private String email;
    @ApiModelProperty(value = "질문", required = true, position = 2, example = "111111")
    private String questionCode;
    @ApiModelProperty(value = "질문", required = true, position = 2, example = "111111")
    private String questionAnswer;
}
