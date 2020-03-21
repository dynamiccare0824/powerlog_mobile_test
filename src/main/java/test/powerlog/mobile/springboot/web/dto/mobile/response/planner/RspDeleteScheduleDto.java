package test.powerlog.mobile.springboot.web.dto.mobile.response.planner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

@Getter
@Setter
@ApiModel
public class RspDeleteScheduleDto<T> extends CommonResponseDto {
    @ApiModelProperty(value = "삭제 수행 여부", position = 3)
    private Boolean isDone;
}
