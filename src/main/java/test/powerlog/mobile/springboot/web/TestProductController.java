package test.powerlog.mobile.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.powerlog.mobile.springboot.domain.products.TestProduct;
import test.powerlog.mobile.springboot.domain.products.TestProductRepository;
import test.powerlog.mobile.springboot.domain.products.UserTable;
import test.powerlog.mobile.springboot.domain.products.UserTableRepository;
import test.powerlog.mobile.springboot.service.SignUpService;
import test.powerlog.mobile.springboot.service.TestProductService;
import test.powerlog.mobile.springboot.web.dto.SignUpDto;
import test.powerlog.mobile.springboot.web.dto.TestProductDto;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TestProductController {

    @Autowired
    private TestProductRepository testProductRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    @Autowired
    TestProductService testProductService;

    @Autowired
    SignUpService signUpService;

    /*로그인 요청 처리*/
    @GetMapping("/login")
    public boolean Login(String id, String password)
    {
        TestProductDto testProductDto = TestProductDto.builder().id(id).password(password).build();
        return testProductService.Login(testProductDto);
    }

    // 회원가입 요청
    @GetMapping("/signup")
    public String Signup(String email, String password, String uid, String name, String gender, String birth, int height, int weight,
                         String agree_fg, String pd_fg, String goal_cd, String certification, String login_fg)
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
    public void create(TestProduct product) {
        testProductRepository.save(product);
    }

//    @GetMapping("/readOne")
//    public Optional readOne(String id) {
//        return testProductRepository.findById(id);
//    }
//
//    @GetMapping("/readAll")
//    public List readAll() {
//        return testProductRepository.findAll();
//    }

//    @GetMapping("/update")
//    public void update(TestProduct product, String id) {
//        testProductRepository.update(product, id);
//    }

    @GetMapping("/delete")
    public void delete(String id) {
        testProductRepository.deleteById(id);
    }
}
