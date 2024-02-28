package ezenweb.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // SMTP : 간이 우편 전송 프로토콜(메일 전송)
        // - 자바에서 메일보내기
        // 1. SMTP 라이브러리 필요.https://start.spring.io/
        // 2. Dependencies : Java Mail Sender

    // 1. java(spring) 지원하는 smtp 객체 필요 = javaMailSender
    @Autowired
    private JavaMailSender javaMailSender; // javamail 제공하는 객체
    public void send(){
        try {
        // * 메일 내용물들을 포맷하기 위한 MIME 형식 객체
        MimeMessage message = javaMailSender.createMimeMessage();
        // 1. 메시지 기본 구성
            // MimeMessageHelper (mime객체, 첨부파일여부, 인코딩타입);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "utf-8");
        // 2. 메시지 보내는 사람
        mimeMessageHelper.setFrom("abc777z@naver.com"); // 관리자 이메일
        // 3. 메시지 받는 사람
        mimeMessageHelper.setTo("abc777z@naver.com"); // 클라이언트(회원) 이메일(매개변수)
        // 4. 메시지 제목
        mimeMessageHelper.setSubject("자바에서 보내온 메시지"); // (매개변수)
        // 5. 메일 내용
        mimeMessageHelper.setText("안녕하세요.내용입니다"); // (매개변수)
        // 메일 전송
        javaMailSender.send(message);
        }catch (Exception e){
            System.out.println("이메일전송 실패 = " + e);
        }
    }
}
