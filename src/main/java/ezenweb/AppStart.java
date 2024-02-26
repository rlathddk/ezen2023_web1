package ezenweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan // 스프링MVC 환경에서 서블릿을 단독적으로 사용할 때
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);
    }

    // 홈페이지 favicon
}
