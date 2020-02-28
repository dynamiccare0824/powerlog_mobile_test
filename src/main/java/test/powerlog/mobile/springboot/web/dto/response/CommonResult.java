package test.powerlog.mobile.springboot.web.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {
    @ApiModelProperty(value = "요청에 대해 정상 처리 되었는지의 여부")
    private boolean isError;

    @ApiModelProperty(value = "응답 메시지")
    private String message;
}

