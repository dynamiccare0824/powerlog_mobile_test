package test.powerlog.mobile.springboot.web.dto.mobile.request.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "", description = "회원가입시 이메일 체크 하는 부분에서 필요한 객체")
public class ReqDupCheckBodyDto {
    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "이름", required = true, position = 1, example = "male")
    private String gender;

    @NotBlank(message = "공백없이 입력하세요.")
    @DecimalMin(value = "19000000", message = "올바른 생년월일을 입력해주세요.")
    @DecimalMax(value = "20200306", message = "올바른 생년월일을 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9]).{8}",
            message = "올바른 값을 입력해주세요")
    @ApiModelProperty(value = "생년월일", required = true, position = 2, example = "19951227")
    private String birth;

    @Pattern(regexp="(?=.*[0-9]).{2,3}",
            message = "올바른 값을 입력해주세요")
//    @DecimalMin(value = "50")
//    @DecimalMax(value = "300")
    @ApiModelProperty(value = "키", required = true, position = 3, example = "180")
    private String height;

    @Pattern(regexp="(?=.*[0-9]).{2,3}",
            message = "올바른 값을 입력해주세요.")
    @DecimalMin(value = "10", message = "올바른 값을 입력해주세요.")
    @DecimalMax(value = "999")
    @ApiModelProperty(value = "몸무게", required = true, position = 4, example = "85")
    private String weight;
}