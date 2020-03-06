package test.powerlog.mobile.springboot.web.dto.mobile.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "", description = "이메일 아이디와 비밀번호를 통해 로그인을 시도하기 위한 객체")
public class ReqRegisterDto {
    @NotBlank(message = "이메일과 비밀번호를 모두 입력해주십시오.")
    @Email(message = "이메일 형식을 맞춰주세요")
    @ApiModelProperty(value = "이메일 아이디", required = true, position = 1, example = "test@test.test")
    private String email;

    @NotBlank(message = "이메일과 비밀번호를 모두 입력해주십시오.")
    @ApiModelProperty(value = "비밀번호", required = true, position = 2, example = "test")
    private String password;

    @NotBlank(message = "이름 정보를 올바르게 입력하세요.")
    @Size(min = 4, message = "이름 정보를 올바르게 입력하세요.")
    @ApiModelProperty(value = "이름", required = true, position = 3, example = "강승연")
    private String name;

    @NotBlank(message = "보안 질문을 선택하세요.")
    @ApiModelProperty(value = "보안 질문", required = true, position = 4, example = "C")
    private String questionCode;

    @NotBlank(message = "보안질문에 대한 답변을 공백 없이 입력해주세요.")
    @Max(value= 40,message = "보안질문에 대한 답변이 너무 깁니다.")
    @ApiModelProperty(value = "보안 질문에 대한 답변", required = true, position = 5, example = "피색")
    private String questionAnswer;

    @NotBlank(message = "성별을 공백없이 male, female 중 하나로 골라주세요.")
    @Max(value= 6, message = "성별의 글자 길이가 너무 깁니다.")
    @ApiModelProperty(value = "보안 질문에 대한 답변", required = true, position = 6, example = "male")
    private String gender;

    @NotBlank(message = "YYYYMMDD의 형태로 입력해주세요")
    @Max(value= 10, message = "입력 형태가 올바른지 확인해주세요")
    @ApiModelProperty(value = "생년월일", required = true, position = 7, example = "19951227")
    private String birth;

    @NotBlank(message = "-없이 입력해주세요")
    @Max(value= 11, message = "입력 형태가 올바른지 확인해주세요")
    @ApiModelProperty(value = "핸드폰 번호", required = true, position = 8, example = "01050055438")
    private String phone;

    @NotBlank(message = "공백 없이 입력해주세요")
    @Max(value= 1, message = "입력 형태가 올바른지 확인해주세요")
    @ApiModelProperty(value = "Desire Body Shape 형태", required = true, position = 9, example = "A")
    private String shapeCode;

    @NotBlank(message = "숫자를 입력해주세요")
    @ApiModelProperty(value = "년 수", required = true, position = 10, example = "10")
    private int careerYear;

    @NotBlank(message = "숫자를 입력해주세요")
    @ApiModelProperty(value = "개월 수", required = true, position = 11, example = "5")
    private int careerMonth;

    @NotBlank(message = "공백 없이 입력해주세요")
    @ApiModelProperty(value = "키", required = true, position = 12, example = "182")
    private int height;

    @NotBlank(message = "공백 없이 입력해주세요")
    @ApiModelProperty(value = "몸무게", required = true, position = 13, example = "95")
    private int weight;
}
