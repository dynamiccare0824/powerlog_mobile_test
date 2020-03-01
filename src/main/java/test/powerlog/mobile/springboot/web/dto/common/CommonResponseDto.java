package test.powerlog.mobile.springboot.web.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.validation.ObjectError;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDto<T> {
    @ApiModelProperty(value = "요청에 대해 정상 처리 되었는지의 여부")
    private Boolean isError;
    @ApiModelProperty(value = "로그인 성공하고 최근 측정 데이터가 있을 때 받는 데이터")
    private List<ObjectError> invalidParamList;
    @ApiModelProperty(value = "응답 메시지")
    private String message;
}

