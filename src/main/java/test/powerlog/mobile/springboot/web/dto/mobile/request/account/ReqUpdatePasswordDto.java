package test.powerlog.mobile.springboot.web.dto.mobile.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdatePasswordDto {
    private String email;

    private String password;
}
