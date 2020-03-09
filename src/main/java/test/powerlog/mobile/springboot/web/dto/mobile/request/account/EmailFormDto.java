package test.powerlog.mobile.springboot.web.dto.mobile.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailFormDto {
    private String sender;

    private String recipient;

    private String subject;

    private String content;
}
