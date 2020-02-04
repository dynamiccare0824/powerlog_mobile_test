package test.powerlog.mobile.springboot.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import test.powerlog.mobile.springboot.domain.products.UserAccount;
import test.powerlog.mobile.springboot.domain.products.UserAccountRepository;
import test.powerlog.mobile.springboot.domain.products.UserTableRepository;
import test.powerlog.mobile.springboot.service.*;
import test.powerlog.mobile.springboot.web.dto.SignUpDto;
import test.powerlog.mobile.springboot.web.dto.UserAccountDto;
import java.util.HashMap;
import java.net.URISyntaxException;


import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    SignUpService signUpService;


//    /*로그인 요청 처리*/
//    @GetMapping("/login")
//    public boolean Login(String id, String password)
//    {
//        UserAccountDto userAccountDto = UserAccountDto.builder().email(id).password(password).build();
//        return loginService.Login(userAccountDto);
//    }

//    @ResponseBody
//    @GetMapping("/login2")
//    public String LoginJson(String id, String password) throws URISyntaxException {
//        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//        SendMsgService_Old sendMsgServiceOld = new SendMsgService_Old(restTemplateBuilder);
//        NumberGen numberGen = new NumberGen();
//
//        String randNum = numberGen.four_digits(4, 1);
//        UserAccountDto userAccountDto = UserAccountDto.builder().email(id).password(password).build();
//        boolean result = loginService.Login(userAccountDto);
//        Map<String, String> map = new HashMap<>();
//        map.put("boolean", Boolean.toString(result));
//        map.put("number", randNum);
//        if (result == true){
//            System.out.println("1");
//            sendMsgServiceOld.send("SMS", "COMM", "01050055438", "01033891768", "안녕하세요" + randNum);
//
//        }
//        return Boolean.toString(result);
//    }

    @ResponseBody
    @GetMapping("/login3")
    public String CheckPhone(String number) throws URISyntaxException, JsonProcessingException {
        SendMsgServic_New sendMsgServic_new = new SendMsgServic_New();
        ObjectMapper mapper = new ObjectMapper();
        NumberGen numberGen = new NumberGen();

        String randNum = numberGen.four_digits(4, 1);
        String[] numbers = {"99999999999"};
        numbers[0] = number;
        HashMap<String, Object> resultMap = new HashMap();
        resultMap.put("type", "SMS");
        resultMap.put("from", "01050055438");
        resultMap.put("to", numbers);
        resultMap.put("content", "인증번호 [" + randNum + "] 숫자 4자리를 입력해주세요 - 파워로그 모바일");
        String json = mapper.writeValueAsString(resultMap);

        String msgMap = sendMsgServic_new.NewSend("https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages", json);
        System.out.println(msgMap);
        return msgMap;
    }


    @PostMapping(value = "/login3")
    public HashMap<String, Object> PostLoginJson(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> resultMap = new HashMap();
        resultMap.put("received_email", userAccountDto.getM_login_vw_email());
        resultMap.put("received_password", userAccountDto.getM_login_vw_password());
        String json = mapper.writeValueAsString(resultMap);
        return resultMap;
    }

    @PostMapping(value = "/login4")
    public HashMap<String, Object> PostLoginJson2(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        String id = userAccountDto.getM_login_vw_email();
        String password =userAccountDto.getM_login_vw_password();
        System.out.println(userAccountRepository.findById(id).get().getM_login_vw_email());
        System.out.println(id + password);
        Boolean result = loginService.Login(id, password);
        resultMap.put("received_password", userAccountDto.getM_login_vw_password());
        resultMap.put("received_email", userAccountDto.getM_login_vw_email());
        resultMap.put("match", result.toString());
        return resultMap;
    }


    // 회원가입 요청
    @GetMapping("/signup")
    public String Signup(String email, String password, String uid, String name, String gender, String birth, int height, int weight,
                         String agree_fg, String pd_fg, String goal_cd, String certification, String login_fg, String cellphone)
    {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println(sqlDate);

        SignUpDto signUpDto  = SignUpDto.builder().email(email).password(password).uid(uid).name(name).gender(gender).birth(birth).height(height).weight(weight)
                .agree_fg(agree_fg).pd_fg(pd_fg).goal_cd(goal_cd).certification(certification).login_fg(login_fg).createdtime(sqlDate).updatedtime(sqlDate).build();
        return signUpService.Signup(signUpDto);
    }

    @GetMapping("/hello2")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/create")
    public void create(UserAccount product) {
        userAccountRepository.save(product);
    }

    @GetMapping("/delete")
    public void delete(String id) {
        userAccountRepository.deleteById(id);
    }
}
