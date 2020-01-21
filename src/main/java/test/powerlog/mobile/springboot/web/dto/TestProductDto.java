package test.powerlog.mobile.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TestProductDto {

    private String id;
    private String password;


    @Builder
    public TestProductDto(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
