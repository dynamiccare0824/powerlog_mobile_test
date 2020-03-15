package test.powerlog.mobile.springboot.web.dto.mobile.response.planner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

@Getter
@Setter
@ApiModel
public class RspByDaySaveDto<T> extends CommonResponseDto {
    @ApiModelProperty(value = "이메일", position = 1, example = "gjgustjd70@naver.com")
    private String email;
    @ApiModelProperty(value = "저장 수행 여부", position = 2)
    private Boolean isDone;
}
