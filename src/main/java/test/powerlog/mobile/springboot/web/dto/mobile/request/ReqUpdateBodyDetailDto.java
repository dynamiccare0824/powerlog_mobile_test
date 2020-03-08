package test.powerlog.mobile.springboot.web.dto.mobile.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdateBodyDetailDto {
    @NotBlank(message = "공백없이 입력하세요.")
    @Email
    @ApiModelProperty(value = "이메일", required = true, position = 1, example = "test@gmail.com")
    private String email;

    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "성별", required = true, position = 2, example = "male")
    private String gender;

    @NotBlank(message = "공백없이 입력하세요.")
    @DecimalMin(value = "19000000", message = "올바른 생년월일을 입력해주세요.")
    @DecimalMax(value = "20200306", message = "올바른 생년월일을 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9]).{8}",
            message = "올바른 값을 입력해주세요")
    @ApiModelProperty(value = "생년월일", required = true, position = 3, example = "19951227")
    private String birth;

    @Pattern(regexp="(?=.*[0-9]).{2,3}",
            message = "올바른 값을 입력해주세요")
    @ApiModelProperty(value = "키", required = true, position = 4, example = "180")
    private String height;

    @Pattern(regexp="(?=.*[0-9]).{2,3}",
            message = "올바른 값을 입력해주세요.")
    @DecimalMin(value = "10", message = "올바른 값을 입력해주세요.")
    @DecimalMax(value = "999")
    @ApiModelProperty(value = "몸무게", required = true, position = 5, example = "85")
    private String weight;
}
