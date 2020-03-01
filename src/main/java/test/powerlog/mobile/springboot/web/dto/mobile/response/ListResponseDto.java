package test.powerlog.mobile.springboot.web.dto.mobile.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

import java.util.List;

@Getter
@Setter
@ApiModel
public class ListResponseDto<T> extends CommonResponseDto {
    @ApiModelProperty(value = "응답 결과")
    private List<T> resultData;
}
