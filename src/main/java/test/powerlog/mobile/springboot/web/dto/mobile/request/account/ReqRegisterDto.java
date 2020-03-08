package test.powerlog.mobile.springboot.web.dto.mobile.request.account;

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
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,20}",
            message = "영문, 숫자, 특수기호를 포함한 6자 - 20자의 비밀번호를 설정해주세요.")
    @ApiModelProperty(value = "비밀번호", required = true, position = 2, example = "test")
    private String password;

    @NotBlank(message = "이름 정보를 올바르게 입력하세요.")
    @Size(min = 2, message = "이름 정보를 올바르게 입력하세요.")
    @ApiModelProperty(value = "이름", required = true, position = 3, example = "강승연")
    private String name;

    @NotBlank(message = "보안 질문을 선택하세요.")
    @ApiModelProperty(value = "보안 질문", required = true, position = 4, example = "C")
    private String questionCode;

    @NotBlank(message = "보안질문에 대한 답변을 공백 없이 입력해주세요.")
    @ApiModelProperty(value = "보안 질문에 대한 답변", required = true, position = 5, example = "피색")
    private String questionAnswer;

    @NotBlank(message = "성별을 공백없이 male, female 중 하나로 골라주세요.")
    @ApiModelProperty(value = "보안 질문에 대한 답변", required = true, position = 6, example = "male")
    private String gender;

    @NotBlank(message = "YYYYMMDD의 형태로 공백없이 입력해주세요")
    @DecimalMin(value = "19000000", message = "올바른 생년월일을 입력해주세요.")
    @DecimalMax(value = "20200306", message = "올바른 생년월일을 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9]).{8}",
            message = "올바른 값을 입력해주세요")
    @ApiModelProperty(value = "생년월일", required = true, position = 7, example = "19951227")
    private String birth;

    @NotBlank(message = "-없이 입력해주세요")
    @Pattern(regexp="(?=.*[0-9]).{10,11}",
            message = "휴대폰 번호를 공백없이 올바르게 입력해주세요")
    @ApiModelProperty(value = "핸드폰 번호", required = true, position = 8, example = "01050055438")
    private String phone;

    @NotBlank(message = "공백 없이 입력해주세요")
    @ApiModelProperty(value = "Desire Body Shape 형태", required = true, position = 9, example = "A")
    private String shapeCode;

    @NotBlank(message = "숫자를 입력해주세요")
    @Pattern(regexp="(?=.*[0-9]).{0,2}",
            message = "올바른 값을 입력해주세요")
    @ApiModelProperty(value = "년 수", required = true, position = 10, example = "10")
    private String careerYear;

    @NotBlank(message = "숫자를 입력해주세요")
    @Pattern(regexp="(?=.*[0-9]).{0,2}",
            message = "올바른 값을 입력해주세요")
    @ApiModelProperty(value = "개월 수", required = true, position = 11, example = "5")
    private String careerMonth;

    @NotBlank(message = "공백 없이 입력해주세요")
    @Pattern(regexp="(?=.*[0-9]).{2,3}",
            message = "올바른 값을 입력해주세요")
    @ApiModelProperty(value = "키", required = true, position = 12, example = "182")
    private String height;

    @NotBlank(message = "공백 없이 입력해주세요")
    @Pattern(regexp="(?=.*[0-9]).{2,3}",
            message = "올바른 값을 입력해주세요")
    @ApiModelProperty(value = "몸무게", required = true, position = 13, example = "95")
    private String weight;
}
