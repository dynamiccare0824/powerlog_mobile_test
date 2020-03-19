package test.powerlog.mobile.springboot.web.dto.mobile.request.planner;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReqPlannerMainDto {
    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "이메일", required = true, position = 1, example = "gjgustjd70@naver.com")
    private String email;
    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "조회 원하는 년도", required = true, position = 2, example = "2020")
    private String year;
    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "조회 원하는 달 1월은 0 2월은 1", required = true, position = 3, example = "2")
    private String month;
}
