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
    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 1)
    private Boolean onSchedule;
    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 2)
    private Boolean isProgram;
    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 3, example = "gjgustjd70@naver.com")
    private String email;
    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 4, example = "YYYYMMDD")
    private String date;
    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 4, example = "YYYYMMDD")
    private String time;
    @NotBlank(message = "공백이 없어야 합니다")
    private String commonCode;
    @NotBlank(message = "공백이 없어야 합니다")
    private int weight;
    @NotBlank(message = "공백이 없어야 합니다")
    private int height;
    @NotBlank(message = "공백이 없어야 합니다")
    private int count;
    @NotBlank(message = "공백이 없어야 합니다")
    private int set;
    @NotBlank(message = "공백이 없어야 합니다")
    private int level;
    @NotBlank(message = "공백이 없어야 합니다")
    private int rest;
    @NotBlank(message = "공백이 없어야 합니다")
    private String device;
}
