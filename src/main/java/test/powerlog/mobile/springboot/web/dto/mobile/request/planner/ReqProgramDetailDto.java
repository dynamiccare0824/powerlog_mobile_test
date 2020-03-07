package test.powerlog.mobile.springboot.web.dto.mobile.request.planner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "", description = "회원가입시 이메일 체크 하는 부분에서 필요한 객체")
public class ReqProgramDetailDto {
    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "이메일", required = true, position = 1, example = "jhn9572@naver.com")
    private String email;

    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "시작 날짜", required = true, position = 2, example = "2020-03-08")
    private String startDate;

    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "주 당 횟수", required = true, position = 3)
    private String numberPerWk;

    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "요일", required = true, position = 4, example = "1,2,3,4")
    private String dayOfWk;
}