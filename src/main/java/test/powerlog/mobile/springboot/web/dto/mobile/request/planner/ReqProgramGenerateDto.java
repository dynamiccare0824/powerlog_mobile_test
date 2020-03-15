package test.powerlog.mobile.springboot.web.dto.mobile.request.planner;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqProgramGenerateDto {
    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "이메일", required = true, position = 1, example = "jhn9572@naver.com")
    private String email;

    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "시작 날짜", required = true, position = 2, example = "20200308")
    private String startDate;

    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "주 당 횟수", required = true, position = 3, example = "4")
    private String numberPerWk;

    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "요일", required = true, position = 4, example = "1,2,3,4")
    private String dayOfWk;
}
