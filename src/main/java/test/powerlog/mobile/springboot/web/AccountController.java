package test.powerlog.mobile.springboot.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.powerlog.mobile.springboot.domain.products.UserAccountVW;
import test.powerlog.mobile.springboot.domain.products.UserAccountVWRepository;
import test.powerlog.mobile.springboot.domain.products.UserTableRepository;
import test.powerlog.mobile.springboot.service.*;
import test.powerlog.mobile.springboot.web.dto.SignUpDto;
import test.powerlog.mobile.springboot.web.dto.UserAccountDto;
import java.util.HashMap;
import java.net.URISyntaxException;

@RestController
public class AccountController {

    @Autowired
    private UserAccountVWRepository userAccountVWRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    SignUpService signUpService;


    @PostMapping(value = "/login")
    public HashMap<String, Object> Login(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        String id = userAccountDto.getVw_email();
        String password =userAccountDto.getVw_password();
        try{
            // match 경우 있거나, 아이디는 존재하는데 비밀번호 틀린 경우
            System.out.println(userAccountVWRepository.findById(id).get().getLoginVwEmail());
            //아이디(이메일)이 존재하지 않는 경우 여기서 catch로 넘어가게 될 것임
            System.out.println(id + password);
            Boolean result = loginService.Login(id, password);
            resultMap.put("received_password", userAccountDto.getVw_password());
            resultMap.put("received_email", userAccountDto.getVw_email());
            resultMap.put("match", result.toString());
        }
        // 아이디가 아예 존재하지 않는 경우
        catch(Exception ex){
            resultMap.put("received_password", userAccountDto.getVw_password());
            resultMap.put("received_email", userAccountDto.getVw_email());
            resultMap.put("match", "false");
        }
        return resultMap;
    }

    @PostMapping(value = "/emailCheck")
    public HashMap<String, Object> EmailCheck(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        String id = userAccountDto.getVw_email();

        //중복 아이디가 있는 경우
        try{
            System.out.println(userAccountVWRepository.findById(id).get().getLoginVwEmail());
            //같은 아이디를 찾을 수 없다면 여기서 catch로 넘어가게 될 것임
            resultMap.put("receivedEmail", userAccountDto.getVw_email());
            resultMap.put("emailPresent", true);
        }
        // 중복 아이디가 없는 경우
        catch(Exception ex){
            resultMap.put("receivedEmail", userAccountDto.getVw_email());
            resultMap.put("emailPresent", false);
        }
        return resultMap;
    }

    @PostMapping(value = "/checkPhone")
    public HashMap<String, Object> PhoneCheck(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
        HashMap<String, Object> tmpMap = new HashMap();
        HashMap<String, Object> resultMap = new HashMap();
        SendMsgServic_New sendMsgServic_new = new SendMsgServic_New();
        String phone = userAccountDto.getVw_phone();
        ObjectMapper mapper = new ObjectMapper();
        NumberGen numberGen = new NumberGen();
        String randNum = numberGen.four_digits(4, 1);
        String[] numbers = {"99999999999"};



        try{
            userAccountVWRepository.findByLoginVwPhone(phone).getLoginVwPhone();
            resultMap.put("phonePresent", true);
            resultMap.put("verificationNum", randNum);
        }
        catch(Exception ex){
            System.out.println(ex);
//             전화번호가 존재하지 않은 경우에만 메시지를 보낸다

            numbers[0] = phone;
            tmpMap.put("type", "SMS");
            tmpMap.put("from", "01050055438");
            tmpMap.put("to", numbers);
            tmpMap.put("content", "인증번호 [" + randNum + "] 숫자 4자리를 입력해주세요 - 파워로그 모바일");
            String json = mapper.writeValueAsString(tmpMap);

            sendMsgServic_new.NewSend("https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages", json);

            resultMap.put("phonePresent", false);
            resultMap.put("verificationNum", randNum);
        }
        return resultMap;
    }



    // 회원가입 요청
    @GetMapping("/signUp")
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
    public void create(UserAccountVW product) {
        userAccountVWRepository.save(product);
    }

    @GetMapping("/delete")
    public void delete(String id) {
        userAccountVWRepository.deleteById(id);
    }
}
