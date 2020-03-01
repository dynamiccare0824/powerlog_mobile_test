package test.powerlog.mobile.springboot.web.dto.mobile.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

@Getter
@Setter
@ApiModel
public class RspDupCheckSendMsg<T> extends CommonResponseDto {
    @ApiModelProperty(value = "DB에 저장된 핸드폰 번호가 있는지 검사해서 알려주는 Boolean 값", position = 1)
    private Boolean phonePresent;
    @ApiModelProperty(value = "메시지를 보내고 나서 받은 결과 String", position = 2,
            example = "{\\\"status\\\":\\\"200\\\",\\\"messages\\\":[{\\\"messageId\\\":\\\"0-ABC-123456-1234567-0\\\",\\\"requestTime\\\":\\\"YYYY-MM-DD HH:MM:00\\\"," +
                    "\\\"from\\\":\\\"0263260717\\\",\\\"to\\\":\\\"01012345678\\\",\\\"contentType\\\":\\\"COMM\\\",\\\"countryCode\\\":\\\"82\\\",\\\"statusCode\\\":\\\"0\\\"}]}")
    private String sendMsgResult;
    @ApiModelProperty(value = "핸드폰 인증번호", position = 3, example = "1234")
    private String verificationNum;
}
