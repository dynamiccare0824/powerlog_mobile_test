package test.powerlog.mobile.springboot.web.dto.kiosk.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ApiModel(value = "", description = "키오스크에서 uid를 받아 계정을 특정하기 위한 객체")
public class RspKioskLoginDto<T> extends CommonResponseDto {

    @ApiModelProperty(value = "고유 번호", required = true, position = 1, example = "0123456789*#")
    private Boolean isPresent;

    @ApiModelProperty(value = "로그인 성공 시 받는 이름", position = 2, example = "홍길동")
    private String name;

    @ApiModelProperty(value = "", position = 3)
    private HashMap<String, Object> wrkotCodeMap;

    @ApiModelProperty(value = "로그인 성공하고 현재 날짜의 운동 데이터가 있다면 데이터를 가져온다.", position = 4)
    private HashMap<String, Object> resultData;

    @ApiModelProperty(value = "이메일", position = 5)
    private String email;
}
