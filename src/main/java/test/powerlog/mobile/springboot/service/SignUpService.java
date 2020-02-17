package test.powerlog.mobile.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.powerlog.mobile.springboot.domain.products.UserTbRepository;
import test.powerlog.mobile.springboot.web.dto.SignUpDto;

@RequiredArgsConstructor
@Service
public class SignUpService {
    @Autowired
    private UserTbRepository userTbRepository;

    /*회원가입 요청 처리*/
    @Transactional
    public String Signup(SignUpDto signUpDto)
    {
        return userTbRepository.save(signUpDto.toEntity()).getUEmail();
    }
}
