package test.powerlog.mobile.springboot.web.dto.kiosk.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "", description = "키오스크에서 uid를 받아 계정을 특정하기 위한 객체")
public class RspKioskLoginDto<T> extends CommonResponseDto {
    @NotBlank(message = "고유번호 12자리를 공백없이 입력해주세요")
    @Size(min = 12, max = 12, message = "고유번호 12자리를 공백없이 입력해주세요.")
    @ApiModelProperty(value = "고유 번호", required = true, position = 1, example = "0123456789*#")
    private Boolean isPresent;

    @ApiModelProperty(value = "로그인 성공 시 받는 이름", position = 2, example = "홍길동")
    private String name;

    @ApiModelProperty(value = "로그인 성공하고 현재 날짜의 운동 데이터가 있다면 데이터를 가져온다.", position = 3)
    private List<T> resultData;
}
