package test.powerlog.mobile.springboot.service.mobile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
public class SendMsgService {

    public String NewSend(String jsonValue, HashMap<String, Object> map) throws IllegalStateException {
//        String sendUrl = "https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages"; 개발자 api sendUrl
        String sendUrl = "https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258120181862:powerlog_mobile/messages";
        String inputLine = null;
        StringBuffer outResult = new StringBuffer();

        try {
            URL url = new URL(sendUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//            conn.setRequestProperty("x-ncp-auth-key", "SmxiQBvfpnf1eaPiJByC"); 개발자 api auth key
            conn.setRequestProperty("x-ncp-auth-key", "iW9gKUc33jHYtD6n9gu2");
//            conn.setRequestProperty("x-ncp-service-secret", "7af448a8032745a68fe8dd7bc5ea000b"); 개발자 service secret
            conn.setRequestProperty("x-ncp-service-secret", "de3418720a484a05af84d08ad78a4f94");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            OutputStream os = conn.getOutputStream();
            os.write(jsonValue.getBytes("UTF-8"));
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((inputLine = in.readLine()) != null) {
                outResult.append(inputLine);
            }

            conn.disconnect();
        } catch (Exception e) {
            map.replace("error", e.toString());
            e.printStackTrace();

        }

        map.put("outResult", outResult.toString());
        return outResult.toString();
    }

    public String buildJson(String phone, String randNum) throws JsonProcessingException {
        String dynamiccareNumber = "0263260717";
        String contents1 = "인증번호 [";
        String contents2 = "] 숫자 4자리를 입력해주세요 - 파워로그 모바일";
        String type = "SMS";

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> tmpMap = new HashMap();
        String[] numbers = {"999999999999"};
        numbers[0] = phone;
        tmpMap.put("type", type);
        tmpMap.put("from", dynamiccareNumber);
        tmpMap.put("to", numbers);
        tmpMap.put("content", contents1 + randNum + contents2);
        String json = mapper.writeValueAsString(tmpMap);

        return json;
    }

    public String buildJsonSendMsg(String phone, String randNum, HashMap<String, Object> map) throws JsonProcessingException {
        return NewSend(buildJson(phone, randNum), map);
    }
}
