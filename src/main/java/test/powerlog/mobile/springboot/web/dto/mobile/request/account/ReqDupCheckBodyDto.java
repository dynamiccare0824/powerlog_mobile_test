package test.powerlog.mobile.springboot.web.dto.mobile.request.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "", description = "회원가입시 이메일 체크 하는 부분에서 필요한 객체")
public class ReqDupCheckBodyDto {
    @NotBlank(message = "공백없이 입력하세요.")
    @ApiModelProperty(value = "이름", required = true, position = 1, example = "강승연")
    private String gender;

    @NotBlank(message = "공백없이 입력하세요.")
    @DecimalMin(value = "19000000")
    @DecimalMax(value = "20200306")
    @ApiModelProperty(value = "생년월일", required = true, position = 2, example = "tmddusgood@gmail.com")
    private String birth;

    @DecimalMin(value = "50")
    @DecimalMax(value = "300")
    private int height;

    @DecimalMin(value = "10")
    @DecimalMax(value = "500")
    @ApiModelProperty(value = "몸무게", required = true, position = 5, example = "111111")
    private int weight;
}