package test.powerlog.mobile.springboot.web.dto.mobile.response.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVw;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ApiModel
public class RspLoginDto<T> extends CommonResponseDto {

    @ApiModelProperty(value = "로그인 성공 시 받는 이름", position = 1, example = "홍길동")
    private String name;
    @ApiModelProperty(value = "로그인 이메일 아이디와 비밀번호 match 여부", position = 2)
    private Boolean isMatch;
    @ApiModelProperty(value = "로그인 성공 시 받는 계정에 존재하는 01 운동의 목록", position = 3)
    private ArrayList resultPresentList;
    @ApiModelProperty(value = "로그인 성공 시 받는 전체 01 운동의 목록", position = 4)
    private ArrayList totalList;
    @ApiModelProperty(value = "로그인 성공하고 최근 측정 데이터가 있을 때 받는 데이터", position = 5)
    private HashMap<String, Object> resultData;
}
