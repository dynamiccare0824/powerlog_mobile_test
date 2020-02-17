package test.powerlog.mobile.springboot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private String sender;

    private String recipient;

    private String subject;

    private String content;
}
