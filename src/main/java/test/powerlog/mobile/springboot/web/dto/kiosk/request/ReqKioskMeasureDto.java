package test.powerlog.mobile.springboot.web.dto.kiosk.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "", description = "키오스크에서 운동 기록을 받아 save 하기 위한 객체")
public class ReqKioskMeasureDto {
    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 3, example = "gjgustjd70@naver.com")
    private String email;

    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 4, example = "A01")
    private String commonCode;

    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 4, example = "A01")
    private int start;

    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 4, example = "A01")
    private int average;

    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 4, example = "A01")
    private int max;

    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 4, example = "A01")
    private int min;

    @NotBlank(message = "공백이 없어야 합니다")
    @ApiModelProperty(position = 11, example = "109239212938")
    private String device;
}
