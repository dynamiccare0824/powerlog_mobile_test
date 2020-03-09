package test.powerlog.mobile.springboot.web.dto.kiosk.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "", description = "키오스크에서 운동 기록을 받아 save 하기 위한 객체")
public class RspKioskMeasureDto extends CommonResponseDto {
    private Boolean isDone;
}
