package test.powerlog.mobile.springboot.web.dto.mobile.response.planner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

import java.util.HashMap;

@Getter
@Setter
@ApiModel
public class RspPlannerMainDto<T> extends CommonResponseDto {

    @ApiModelProperty(value = "이메일", position = 1, example = "gjgustjd70@naver.com")
    private String email;
    @ApiModelProperty(value = "달성률", position = 2)
    private String achievementRate;
    @ApiModelProperty(value = "출석 수", position = 3)
    private String attendance;
    @ApiModelProperty(value = "데이터가 있을 때 받는 일정 데이터", position = 4)
    private HashMap<String, Object> resultData;
}
