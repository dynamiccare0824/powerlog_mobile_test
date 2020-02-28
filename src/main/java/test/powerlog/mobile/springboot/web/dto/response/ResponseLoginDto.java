package test.powerlog.mobile.springboot.web.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
public class ResponseLoginDto<T> extends CommonResult {
    @ApiModelProperty(value = "로그인 응답 성공 시 받는 데이터")
    private List<T> resultData;
    @ApiModelProperty(value = "로그인 이메일 아이디와 비밀번호 match 여부")
    private Boolean isMatch;
}
