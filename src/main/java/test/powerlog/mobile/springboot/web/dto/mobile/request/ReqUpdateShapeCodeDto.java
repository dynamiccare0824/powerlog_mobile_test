package test.powerlog.mobile.springboot.web.dto.mobile.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdateShapeCodeDto {
    private String email;
    private String shapeCode;
}
