package test.powerlog.mobile.springboot.web.dto.request;

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
public class ReqDupCheckEmailDto {
//    @NotBlank(message = "이름 정보를 올바르게 입력하세요.")
//    @Size(min = 4, message = "이름 정보를 올바르게 입력하세요.")
    @ApiModelProperty(value = "이름", required = true, position = 1, example = "강승연")
    private String name;

//    @NotBlank(message = "이메일 정보를 올바르게 입력하세요.")
//    @Email(message = "이메일 정보를 올바르게 입력하세요.")
//    @Size(min = 12, max = 30, message = "이메일은 12자에서 30자 사이의 길이여야 합니다.")
    @ApiModelProperty(value = "이메일 아이디", required = true, position = 2, example = "tmddusgood@gmail.com")
    private String email;

//    @NotBlank(message = "보안 질문을 선택하세요.")
    @ApiModelProperty(value = "보안 질문", required = true, position = 3, example = "C")
    private String questionCode;

    @NotBlank(message = "보안질문에 대한 답변을 공백 없이 입력해주세요.")
    @Max(value= 40,message = "보안질문에 대한 답변이 너무 깁니다.")
    @ApiModelProperty(value = "보안 질문에 대한 답변", required = true, position = 4, example = "피색")
    private String questionAnswer;

    @NotBlank
    @Size(min = 6, max = 20)
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 6자 ~ 20자의 비밀번호여야 합니다.")
    @ApiModelProperty(value = "비밀번호", required = true, position = 5, example = "111111")
    private String password;
}
