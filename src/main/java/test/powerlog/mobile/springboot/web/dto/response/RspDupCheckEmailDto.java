package test.powerlog.mobile.springboot.web.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
public class RspDupCheckEmailDto<T> extends CommonResult {
    @ApiModelProperty(value = "true면 DB에 등록된 이메일이 이미 존재한다.")
    private Boolean emailPresent;
}
