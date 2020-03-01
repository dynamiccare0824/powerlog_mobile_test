package test.powerlog.mobile.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//public class Application extends SpringBootServletInitializer {
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
