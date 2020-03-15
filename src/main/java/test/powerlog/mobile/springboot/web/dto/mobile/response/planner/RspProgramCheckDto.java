package test.powerlog.mobile.springboot.web.dto.mobile.response.planner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@ApiModel
public class RspProgramCheckDto<T> extends CommonResponseDto {
    @ApiModelProperty(value = "이메일", position = 1, example = "gjgustjd70@naver.com")
    private String email;
    @ApiModelProperty(value = "생성된 프로그램 존재 여부", position = 2)
    private Boolean isPresent;
}
