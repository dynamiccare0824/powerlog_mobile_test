package test.powerlog.mobile.springboot.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "중복 검사를 위해서 넘겨줄 이메일 아이디가 포함된 객체", description = "이 객체는 오로지 email 파라미터 하나만을 가진다.")
public class EmailOnlyDto {
    @ApiModelProperty(value = "중복 검사를 받을 이메일 아이디", required = true, position = 1, example = "test@gmail.com")
    private String email;
}
