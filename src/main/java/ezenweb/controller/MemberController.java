package ezenweb.controller;

import ezenweb.model.dao.MemberDao;
import ezenweb.model.dto.LoginDto;
import ezenweb.model.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    // 1단계. V<---->C 사이의 HTTP 통신 방식 설계
    // 2단계. Controller mapping 함수 선언 하고 통신 체크 ( API Tester )
    // 3단계. Controller request 매개변수 매핑
    // -------------- Dto , Service ---------------//
    // 4단계. 응답 : 1.뷰 반환 : text/html;  VS  2. 데이터/값 : @ResponseBody : Application/JSON
    @Autowired
    private MemberDao memberDao;
    // 1.=========== 회원가입 처리 요청 ===============
    @PostMapping("/member/signup") // http://localhost:80/member/signup
    @ResponseBody // 응답 방식 application/json;
    public boolean doPostSignup( MemberDto memberDto ){
        /* params  {   id ="아이디" , pw ="비밀번호" , name="이름" ,   email="이메일" , phone="전화번호" , img ="프로필사진"   }  */
        System.out.println("MemberController.signup");
        System.out.println("memberDto = " + memberDto);
        // --
        boolean result = memberDao.doPostSignup(memberDto);//Dao처리;
        return result; // Dao 요청후 응답 결과를 보내기.
    }

    // * Http 요청 객체
    @Autowired // 객체만들기
    private HttpServletRequest request;

    // 2. =========== 로그인 처리 요청 ===============
    @PostMapping("/member/login") // http://localhost:80/member/login
    @ResponseBody  // 응답 방식 application/json;
    public boolean doPostLogin(LoginDto loginDto ){
        /* params    { id ="아이디" , pw ="비밀번호"  }   */
        // --
        boolean result = memberDao.doPostLogin(loginDto); // Dao처리
        if(result){
            request.getSession().setAttribute("loginDto", loginDto.getId());
        }
        // 로그인 성공시 (세션) 세션 : 톰캣서버에 내장되어있는 서버
            // 세션 저장소 : 톰캣서버에 브라우저 마다의 메모리 할당
            // 세션 객체 타입 : Object(여러가지의 타입들을 저장할려고)
            // 1. Http세션 요청 객체 호출. HttpServletRequest
            // 2. Http세션 호출 .getSession()
            // 3. Http세션 데이터 저장 .setAttribute("세션명", 데이터);      -- 자동 형변환 ( 자 --> 부)
            // - Http세션 데이터 호출  .getAttribute("세션속성명")           -- 강제형 형변환 ( 부 --> 자) / 캐스팅
            // - .invalidate
        return result; // Dao 요청후 응답 결과를 보내기
    }//  f end
    // 2-2 ========== 로그인 정보 확인 요청 ==============
    @GetMapping("/member/login/check")
    @ResponseBody
    public String doGetLoingCheck(){
        // * 로그인 여부 확인 = 세션이 있다 없다 확인
            // 1-> http 요청 객체 호출, 2->http세션 객체 호출 -> 3.http 세션 데이터 호출
        // null 형번환이 불가능하기 때문에 유효성검사
        String loginDto = null;
        Object sessionObj = request.getSession().getAttribute("loginDto");
        if(sessionObj != null) {loginDto = (String) sessionObj;}
        // 만약에 로그인했으면(세션에 데이터가 있으면) 강제형변환을 통해 데이터 호출 아니면 0
        return loginDto;
    } // f end
    // 2-3 ========== 로그인 로그아웃 / 세션 초기화 ==============
    @GetMapping("/member/logout")
    @ResponseBody // 응답받을 대상이 JS ajax
    public boolean doGetLoginOut(){

        // 1. 로그인 관련 세션 초기화(모든 세션의 속성이 초기화 -> 로그인세션 외 다른 세션도 고려)
            // 1. 모든 세션 초기화 (쇼핑몰=장바구니)
            request.getSession().invalidate(); // 현재 요청 보낸 브라우저의 세션 초기화
            // 2. 특정 세션속성 초기화 => 동일한 세션속성명에 null 대입한다
            // request.getSession().setAttribute("loginDto", null);
            return true;
            // 로그아웃 성공시 -> 메인페이지 또는 로그인페이지 이동
    }
    // 3. =========== 회원가입 페이지 요청 ===============
    @GetMapping("/member/signup")
    public String viewSignup(){
        System.out.println("MemberController.viewSignup");
        return "ezenweb/signup";
    }
    // 4. =========== 로그인 페이지 요청 ===============
    @GetMapping("/member/login")
    public String viewLogin(){
        System.out.println("MemberController.viewLogin");
        return "ezenweb/login";
    }
    // 수정 1단계 : 기존 데이터 불러오기
    @GetMapping("/member/{no}/edit")
    public String edit(@PathVariable int no){
        System.out.println("MemberController.vieweEdit");
        // 3. 뷰 페이지 설정
        return "ezenweb/edit";
    }
} // c e