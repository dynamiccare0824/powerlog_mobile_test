package test.powerlog.mobile.springboot.web.dto.mobile.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

@Getter
@Setter
@ApiModel
public class FailListResponseDtoDto<T> extends CommonResponseDto {
    @ApiModelProperty(value = "응답 결과")
    private T resultData;
}
