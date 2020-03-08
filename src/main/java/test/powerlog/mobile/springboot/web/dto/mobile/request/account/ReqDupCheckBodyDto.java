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
    @ApiModelProperty(value = "이름", required = true, position = 1, example = "강승연")
    private String gender;

    @NotBlank(message = "공백없이 입력하세요.")
    @DecimalMin(value = "19000000", message = "올바른 생년월일을 입력해주세요.")
    @DecimalMax(value = "20200306", message = "올바른 생년월일을 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9]){8}",
            message = "올바른 값을 입력해주세요")
    @ApiModelProperty(value = "생년월일", required = true, position = 2, example = "tmddusgood@gmail.com")
    private String birth;

    @Pattern(regexp="(?=.*[0-9]){2,3}",
            message = "올바른 값을 입력해주세요")
    @DecimalMin(value = "50")
    @DecimalMax(value = "300")
    private int height;

    @Pattern(regexp="(?=.*[0-9]){2,3}",
            message = "올바른 값을 입력해주세요.")
    @DecimalMin(value = "10", message = "올바른 값을 입력해주세요.")
    @DecimalMax(value = "999")
    @ApiModelProperty(value = "몸무게", required = true, position = 5, example = "111111")
    private int weight;
}