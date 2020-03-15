package test.powerlog.mobile.springboot.web.dto.mobile.request.planner;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqByDaySaveDto {
    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "이메일", required = true, position = 1, example = "jhn9572@naver.com")
    private String email;

    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "운동 종류", required = true, position = 2, example = "yyyyMMdd")
    private String date;

    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "운동 종류", required = true, position = 2, example = "A02")
    private String commonCode;

    @ApiModelProperty(value = "주 당 횟수", required = true, position = 3)
    private int weight;

    @ApiModelProperty(value = "등속성의 경우 레벨, 아니라면 null", required = true, position = 3)
    private int level;

    @ApiModelProperty(value = "일반운동의 경우 횟수, 아니라면 null", required = true, position = 4)
    private int count;

    @ApiModelProperty(value = "세트 수", required = true, position = 5)
    private int set;

    @ApiModelProperty(value = "쉬는 시간", required = true, position = 6)
    private int rest;
}
