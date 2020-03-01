package test.powerlog.mobile.springboot.web.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ApiModel
public class RspLoginDto<T> extends CommonResult {
    @ApiModelProperty(value = "로그인 성공하고 최근 측정 데이터가 있을 때 받는 데이터")
    private List<T> resultData;
    @ApiModelProperty(value = "로그인 이메일 아이디와 비밀번호 match 여부", position = 2, example = "true")
    private Boolean isMatch;
    @ApiModelProperty(value = "로그인 성공 시 받는 이름", position = 1, example = "홍길동")
    private String name;
}
