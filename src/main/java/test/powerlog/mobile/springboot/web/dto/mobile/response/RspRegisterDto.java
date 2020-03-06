package test.powerlog.mobile.springboot.web.dto.mobile.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel
public class RspRegisterDto extends CommonResponseDto {

    @ApiModelProperty(value = "계정 존재 여부")
    private Boolean isPresent;
}
