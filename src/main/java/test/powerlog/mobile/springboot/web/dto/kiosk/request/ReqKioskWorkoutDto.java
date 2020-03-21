package test.powerlog.mobile.springboot.web.dto.kiosk.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "", description = "키오스크에서 운동 기록을 받아 save 하기 위한 객체")
public class ReqKioskWorkoutDto {
    @ApiModelProperty(position = 1)
    private Boolean onSchedule;

    @ApiModelProperty(position = 2)
    private Boolean isProgram;

    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 3, example = "gjgustjd70@naver.com")
    private String email;

    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 4, example = "A01")
    private String commonCode;

//    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 5)
    private int weight;

//    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 6)
    private int height;

//    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 7)
    private int count;

//    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 8)
    private int set;

//    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 9)
    private int level;

//    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 10)
    private int rest;

    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 11, example = "109239212938")
    private String device;

    @ApiModelProperty(value = "만약 onSchedule = true 인 경우 갖는 index", required = true, position = 12)
    private String index;
}
