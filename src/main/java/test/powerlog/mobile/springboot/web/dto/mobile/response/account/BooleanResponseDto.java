package test.powerlog.mobile.springboot.web.dto.mobile.response.account;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel
public class BooleanResponseDto {
    private Boolean emailPresent;
    private Boolean phonePresent;
    private Boolean match;
    private String verificationNum;
    private String uid;

    @Builder
    public BooleanResponseDto(Boolean emailPresent, Boolean phonePresent, Boolean match, String verificationNum, String uid){
        this.emailPresent = emailPresent;
        this.phonePresent = phonePresent;
        this.match = match;
        this.verificationNum = verificationNum;
        this.uid = uid;
    }
}
