package test.powerlog.mobile.springboot.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetDto {
    private String email;

    private String password;

    private String shapeCode;

    private String phone;
}
