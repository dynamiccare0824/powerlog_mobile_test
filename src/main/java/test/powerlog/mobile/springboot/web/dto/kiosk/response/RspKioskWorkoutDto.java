package test.powerlog.mobile.springboot.web.dto.kiosk.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "", description = "키오스크에서 운동 기록을 받아 save 하기 위한 객체")
public class RspKioskWorkoutDto extends CommonResponseDto {
    private Boolean isDone;
}
