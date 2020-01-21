package test.powerlog.mobile.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.products.TestProduct;
import test.powerlog.mobile.springboot.domain.products.TestProductRepository;
import test.powerlog.mobile.springboot.web.dto.TestProductDto;
import java.util.Optional;

@Service
public class TestProductService {

    @Autowired
    private TestProductRepository testProductRepository;

    /*로그인 요청 처리*/
    public boolean Login(TestProductDto testProductDto) {
        boolean result = false;
        try {
            Optional<TestProduct> record = testProductRepository.findById(testProductDto.getId());

            if (record.get().getId().equals(testProductDto.getId()) && record.get().getPassword().equals(testProductDto.getPassword())) {
                System.out.println(record.get().getId());
                System.out.println("Correct");
                result = true;
            } else {
                System.out.println(record.get().getId());
                System.out.println("Error");
                result = false;
            }//            return (record.get().getId() == testProductDto.getId() && record.get().getPassword() == testProductDto.getPassword()) ? true: false
        } catch (Exception ex) {
            System.out.println("Error");
        }
        System.out.println("Finally");
        return result;
    }
}
