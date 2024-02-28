package ezenweb.controller;

import ezenweb.Service.MemberService;
import ezenweb.model.dao.MemberDao;
import ezenweb.model.dto.LoginDto;
import ezenweb.model.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Controller
public class MemberController {
    // 1단계. V<---->C 사이의 HTTP 통신 방식 설계
    // 2단계. Controller mapping 함수 선언 하고 통신 체크 ( API Tester )
    // 3단계. Controller request 매개변수 매핑
    // -------------- Dto , Service ---------------//
    // 4단계. 응답 : 1.뷰 반환 : text/html;  VS  2. 데이터/값 : @ResponseBody : Application/JSON
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberDao memberDao;
    // 1.=========== 회원가입 처리 요청 ===============
    @PostMapping("/member/signup") // http://localhost:80/member/signup
    @ResponseBody // 응답 방식 application/json;
    public boolean doPostSignup( MemberDto memberDto ){
        return memberService.doPostSignup(memberDto); // Dao 요청후 응답 결과를 보내기.
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
        if(result){ // 만약에 로그인 성공이면 세션 부여

            // 세션에 저장할 내용물들을 구성(식별키 만)
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
    // ============== 아이디 중복 체크 요청 =================
    @GetMapping("/member/find/idcheck")
    @ResponseBody
    public boolean doGetFindIdCheck(String id){
        System.out.println("MemberController.doGetFindIdCheck");
        System.out.println("id = " + id);
        return memberService.doGetFindIdCheck(id);
    }
    // ========= 회원정보 요청
    @GetMapping("/member/login/info")
    @ResponseBody
    public MemberDto doGetloginInfo(String id){
        return memberService.doGetLoginInfo(id); // 서비스 요청과 응답 전달
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

/* params  {   id ="아이디" , pw ="비밀번호" , name="이름" ,   email="이메일" , phone="전화번호" , img ="프로필사진"   }  */
//MultipartFile 첨부파일 = memberDto.getImg();
//        System.out.println(첨부파일);   // 첨부파일이 들어있는 객체 주소
//        System.out.println(첨부파일.getSize()); // 첨부파일 용량 : 18465 바이트
//        System.out.println(첨부파일.getContentType()); // image/png : 첨부파일의 확장자
//        System.out.println(첨부파일.getOriginalFilename()); // logo.png : 첨부파일의 이름 (확장자포함)
//        System.out.println(첨부파일.getName()); // img : form input name
//
//
//// 서버에 업로드 했을 때 설계
//// 1. 여러 클라이언트[다수]가 동일한 파일명으로 서버[1명]에게 업로드 했을 때 [식별깨짐]
//// 식별이름 : 1.(아이디어) 날짜조합+데이터 2. UUID (난수생성, 가독성 떨어짐)
//// 2. 클라이언트 화면 표시
//// 업로드 경로 : 톰캣(static)에 업로드
//
///*
//    클라이언트 ----------> 톰캣(서버) <--------- build --------- 개발자
//                요청                           컴파일           코드
//             <---------
//*/
//// * 업로드 할 경로 설정(내장 톰캣 경로)
//String uploadPath = "C:\\Users\\504\\Downloads\\ezen2023_web1\\build\\resources\\main\\static\\img";
//
//
//
//// * 파일 이름 조합하기 : 새로운 식별이름과 실제 파일 이름
//// 식별키와 실제 이름 구분 : 왜 ?? 나중에 쪼개서 구분할려고 [다운로드시 식별키 빼고 제공할려고]
//// 혹시나 파일 이름이 _가 있을 경우 기준이 깨짐
//// .replaceAll() : 문자열 치환/교체해버리기
//String uuid = UUID.randomUUID().toString();
//        System.out.println("uuid = " + uuid);
//String filename = uuid+"_"+memberDto.getImg().getOriginalFilename().replaceAll("_","-");
//        memberDto.setUuidFile(filename); // 조합된 파일명으로 db처리 할려고
//// 1. 첨부파일 업로드 하기 [업로드란 : 클라이언트의 바이트(대용량/파일)을 서버로 복사]
//// 1. [어디에] 첨부파일을 저장할 경로
//// File 클래스 : 파일 관련된 메소드 제공
//// new File (파일경로);
//
//File file = new File("uploadPath+filename");
//        System.out.println("file.exists = " + file.exists()); // c:\java\text.png
//        // 2. [무엇을] 첨부파일 객체
//        // /.transferTo(경로)
//        try {
//        memberDto.getImg().transferTo(file);
//        }catch (Exception e) {
//        System.out.println("e = " + e);
//        }