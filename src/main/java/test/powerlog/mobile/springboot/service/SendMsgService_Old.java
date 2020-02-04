package test.powerlog.mobile.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class SendMsgService_Old {
    private RestTemplate restTemplate;
    private final String ApiUrl = "https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages";

    public SendMsgService_Old(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void send(String type, String contentType, String from, String to, String content) throws URISyntaxException {
        String[] array = {"01033891768"};
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("type", type);
        map.add("contentType", contentType);
        map.add("from", from);
        map.add("to", array);
        map.add("content", content);
        System.out.println(map);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=utf-8");
        httpHeaders.add("x-ncp-auth-key", "SmxiQBvfpnf1eaPiJByC");
        httpHeaders.add("x-ncp-service-secret", "7af448a8032745a68fe8dd7bc5ea000b");


        System.out.println("4");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(map, httpHeaders);
        System.out.println(httpEntity);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(new URI(ApiUrl), httpEntity, String.class);
        System.out.println(responseEntity);
    }
}
