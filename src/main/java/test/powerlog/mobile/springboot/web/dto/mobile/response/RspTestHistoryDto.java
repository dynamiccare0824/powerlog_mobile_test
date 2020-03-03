package test.powerlog.mobile.springboot.web.dto.mobile.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@ApiModel
public class RspTestHistoryDto<T> extends CommonResponseDto {

    @ApiModelProperty(value = "로그인 이메일 아이디와 비밀번호 match 여부", position = 2, example = "true")
    private Boolean isMatch;
    @ApiModelProperty(value = "로그인 성공 시 받는 이름", position = 1, example = "홍길동")
    private String name;
    @ApiModelProperty(value = "로그인 성공 시 받는 ResultList", position = 3, example = "{A01, B02}")
    private ArrayList resultList;
    @ApiModelProperty(value = "로그인 성공하고 최근 측정 데이터가 있을 때 받는 데이터", position = 4)
    private HashMap<String, Object> resultData;
}
