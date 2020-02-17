package test.powerlog.mobile.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.powerlog.mobile.springboot.domain.products.UserTb;
import java.sql.Date;

@Getter
@NoArgsConstructor
public class LogLateMsrDto {


    private String email;
    private String logDate;
    private String commonCode;
    private String max;
    private String xy;



    @Builder
    LogLateMsrDto(String email, String logDate, String commonCode, String max, String xy){
        this.email = email;
        this.logDate = logDate;
        this.commonCode = commonCode;
        this.max = max;
        this.xy = xy;
    }
}
