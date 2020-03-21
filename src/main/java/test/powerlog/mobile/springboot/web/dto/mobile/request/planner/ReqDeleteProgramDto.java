package test.powerlog.mobile.springboot.web.dto.mobile.request.planner;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqDeleteProgramDto {
    @ApiModelProperty(value = "이메일", required = true, position = 1, example = "gjgustjd70@naver.com")
    private String email;
}
