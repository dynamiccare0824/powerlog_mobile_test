package test.powerlog.mobile.springboot.web.dto.mobile.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

@Getter
@Setter
@ApiModel
public class RspDeleteUserDto<T> extends CommonResponseDto {
    @ApiModelProperty(value = "로그인 이메일 아이디와 비밀번호 match 여부")
    private Boolean isMatch;
}
