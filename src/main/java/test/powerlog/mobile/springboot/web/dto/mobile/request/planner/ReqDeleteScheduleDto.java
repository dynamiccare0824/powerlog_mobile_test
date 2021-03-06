package test.powerlog.mobile.springboot.web.dto.mobile.request.planner;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqDeleteScheduleDto {
    @ApiModelProperty(value = "운동이 가지고 있는 unique index", required = true, position = 1, example = "1 gjgustjd70@naver.com false")
    private String index;
}
