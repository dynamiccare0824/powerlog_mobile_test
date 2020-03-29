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
public class RspProgramGenerateDto<T> extends CommonResponseDto {
    @ApiModelProperty(value = "이메일", position = 1, example = "gjgustjd70@naver.com")
    private String email;
    @ApiModelProperty(value = "데이터가 있을 때 받는 일정 데이터", position = 2)
    private HashMap<String, Object> resultData;
    @ApiModelProperty(value = "안 한 운동의 리스트", position = 3)
    private ArrayList nullList;
//    @ApiModelProperty(value = "달성률", position = 4)
//    private String achievementRate;
//    @ApiModelProperty(value = "출석 수", position = 5)
//    private String attendance;
}
