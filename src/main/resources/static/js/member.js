// 1. 회원가입
function signup(){
    console.log('signup()')

    // * 유효성검사 체크 현황중에 하나라도 false이면 회원가입 금지
    for(let i = 0; i<checkArray.length; i++){
        if(!checkArray[i]){
            alert('입력사항들을 모두 정확히 입력해주세요')
            return;
        }
    }

    // 1. HTML 입력값 호출 [document.querySelector()]
    // 1. 데이터 하나씩 요청
//    let id = document.querySelector('#id').value; //#id 또는 .class 또는 [name]
//    let pw = document.querySelector('#pw').value;
//    let name = document.querySelector('#name').value;
//    let phone = document.querySelector('#phone').value;
//    let email = document.querySelector('#email').value;
//    let img = document.querySelector('#img').value;
    // 2.  폼 가져오기
    let signUpForm = document.querySelectorAll('.signUpForm');
    console.log(signUpForm);
    let signUpFormData = new FormData (signUpForm[0]);
    console.log(signUpFormData);
    // -- 유효성검사
    // 2. 객체화 [ let info = {} ]
//    let info = {
//        id : id, pw : pw, name : name, phone : phone, email : email, img : img
//    }; console.log(info);
    // 3. controller 통신(AJAX) -> 원래는 객체를 배열에 저장했었음
    $.ajax({
            url : '/member/signup' , //controller 주소 매핑
            method : 'POST' , //
            //data : info,
            data:signUpFormData,
            contentType : false,
            processData : false,
            success : (r) =>{ // controller 응답 받은 매개변수
            console.log(r);
            // 4. 결과
            if(r){
                alert('회원가입 성공');
                location.href = '/member/login'
            }else{
                alert('회원가입 실패');
            }
                console.log(r);
            }
        })
    } // 회원가입 e

    /*
        onclick     : 클릭 할때 마다
        onchange    : 값이 변경될때 마다
        onkeyup     : 키보드 키를 떼었을때.

        ------ 정규표현식 ------
        정규표현식란 : 특정한 규칙을 가진 문자열의 집합을 표현할때 사용하는 형식 언어
            - 주로 문자열 데이터 검사할때 사용 - 유효성검사.
            - 메소드
                정규표현식.test( 검사할대상 );
            - 형식 규칙
                /^      : 정규표현식 시작 알림.
                $/      : 정규표현식 끝 알림
                { 최소길이 , 최대길이 }    : 허용 문자 길이 규칙
                [ 허용할 문자/숫자 ]             : 허용 문자 규칙 규칙
                    [a-z]                   : 소문자 a ~ z 허용
                    [a-zA-Z]                : 영 대소문자 a ~ z 허용
                    [a-zA-Z0-9]             : 영 대소문자 , 숫자 허용
                    [a-zA-Z0-9가-힣]        : 영 대소문자 , 숫자 , 한글 허용
                    [ac]                    : a 또는 c 허용
                +   : 앞 에 있는 패턴 1개 이상 반복
                ?   : 앞 에 있는 패턴 0개 혹은 1개 이상 반복
                *   : 앞 에 있느 패턴 0개 반복
                .   : 1개 문자
                    (?=.*[1개이상문자패턴])
                ( ) : 패턴의 그룹
                ?!.* : 문자열내 존재하지 않음.
                ?=.* : 문자열내 존재함.

                예1)  /^[a-z0-9]{5,30}$/
                    영 소문자와숫자 조합의 5~30글자 허용
                예2)  /^[A-Za-z0-9]{5,30}$/
                    영 대소문자와숫자 조합의 5~30글자 허용
                예3) (?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{5,30}
                    영 대소문자 1개 이상 필수 , 숫자 1개 이상 필수
    */
   // 현재 유효성검사 체크 현황
   let checkArray = [false,false,false,false,false] // 아이디, 비밀번호, 이름, 전화번호, 이메일
    // 4. 아이디 유효성검사(아이디 입력할 때 마다)
    function idcheck(){
        // 1. 입력된 데이터 가져오기
        console.log('idCheck()');
        let id = document.querySelector('#id').value;
        console.log(id);

        // 2. 정규표현식 : 영소문자+숫자 조합의 5~30 글자 사이 규칙
        let 아이디규칙 = /^[a-z0-9]{5,30}$/

        // 3. 정규표현식에 따른 검사
        console.log(아이디규칙.test(id));
        if(아이디규칙.test(id)){ 
            // * 아이디 중복체크 (ajax)
            $.ajax({ // 비동기 vs 동기 
                method: "get", // HTTP BODY --> 없다 --> 쿼리스트링
                url: "/member/find/idcheck",
                data: {"id" : id},
                success: function (r) {
                    if(r){
                        document.querySelector('.idcheckbox').innerHTML = `사용중인 아이디입니다.`;
                        checkArray[0] = false; // 체크 현황 변경
                    }else{
                        document.querySelector('.idcheckbox').innerHTML = `통과`;
                        checkArray[0] = true; // 체크 현황 변경
                    }
                } // success end
            }); // ajax end
        }else{
            // 유효성 검사 결과 출력
            document.querySelector('.idcheckbox').innerHTML = `영소문자+숫자 조합의 5~30글자 사이로 입력해주세요.`;
            checkArray[0] = true; // 체크 현황 변경
        }
    } // f e

// 5.
function pwcheck(){
    // 1. 입력값 가져온다
    let pw = document.querySelector('#pw').value;
    let pwconfirm = document.querySelector('#pwconfirm').value;

    // 2. 유효성검사
    let msg = "통과";
    checkArray[1] = false;
        // 1. 비밀번호에 대한 정규표현식 : 영대소문자 1개 필수와 숫자 1개 필수의 조합 5~30글자
        let 비밀번호규칙 = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/
        //숫자+영문자+특수문자 조합으로 8자리 이상 사용해야 합니다

        // 2.
        if(비밀번호규칙.test(pw)){    // 비밀번호확인 정규식 검사
           if(비밀번호규칙.test(pwconfirm)){ // 비밀번호확인 정규식 검사
                if(pw == pwconfirm) {  // 일치
                    msg = "통과"
                    checkArray[1] = true; // 체크 현황 변경
                }else {
                    msg = "패스워드 불일치";
                }
           }else {
                msg = "숫자+영문자+특수문자 조합으로 8자리 이상 사용해야 합니다"
           }
        }else {
            msg = "숫자+영문자+특수문자 조합으로 8자리 이상 사용해야 합니다";
        }
    document.querySelector('.pwcheckbox').innerHTML = msg;
}
// 6. 이름 유효성검사
function namecheck(){
    let name = document.querySelector('#name').value; // 1. 입력값 가져온다
    let 이름규칙 = /^[가-힣]{3,20}$/      // 2. 정규표현식 작성한다
    if(이름규칙.test(name)){             // 3. 정규표현식 검사한다
        msg = "통과" ; checkArray[2] = true; // 4. 정규표현식 검사가 일치했을 떄
        checkArray[2] = true; // 체크 현황 변경
    }else{
        msg = "3글자이상 입력해주세요" ;
    }
    document.querySelector('.namecheckbox').innerHTML = msg;
}

// 7. 전화번호 유효성검사 : 000-0000-0000 또는 00-000-0000
function phonecheck(){
    let phone = document.querySelector('#phone').value;
    let 전화번호규칙 = /^([0-9]{2,3})+[-]+([0-9]{3,4})+[-]+([0-9]{4})+$/

    let msg = '000-0000-0000 또는 00-000-0000 입력해주세요'
    checkArray[3] = false;

    if(전화번호규칙.test(phone)){             // 3. 정규표현식 검사한다
            msg = "통과" ; checkArray[3] = true; // 4. 정규표현식 검사가 일치했을 떄
            checkArray[3] = true; // 체크 현황 변경
        }
        document.querySelector('.phonecheckbox').innerHTML = msg;
}

// 8. 이메일 유효성검사, 문자@문자 , 인증 통한 검토
function emailcheck(){
    let email = document.querySelector('#email').value;
    let 이메일규칙 = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z]+$/

    let msg = '아이디@도메인 입력해주세요'
    checkArray[4] = false;
    authreqbtn.disabled = true; // 인증요청 버튼 사용

    if(이메일규칙.test(email)){             // 3. 정규표현식 검사한다
        authreqbtn.disabled = false; // 인증요청 버튼 사용
        msg = '인증요청가능';
    }
    document.querySelector('.emailcheckbox').innerHTML = msg;
}
// 2. 로그인
function login(){
    console.log("login()");
    // 1. HTML 입력값 호출
    let id = document.querySelector('#id').value; console.log(id);
    let pw = document.querySelector('#pw').value; console.log(pw);
    // 2. 객체화
    let info = {id : id, pw : pw };
    console.log(info);
    // 3. 서버와 통신
    $.ajax({
                url : '/member/login',
                method : 'POST',
                data : info,
                success : function (result){
                // 4. 결과
                if(result){
                    alert('로그인 성공');
                    /* JS 페이지 전환 */
                    location.href ="/" // 로그인 성공시 메인페이지로
                }else{
                    alert('로그인 실패');
                }
                    console.log(result);
                }
            })
    // 4. 결과
} // 로그인 e

/*
    onclick
    onchange

*/
function onChangeImg( event ){
    console.log('preimg');
    console.log(event); // 현재 함수를 실행한 input
    console.log(event.files); // 현재input의 첨부파일들
    console.log(event.files[0]); // 첨부파일등 중에서 첫번째 파일

    // input에 업로드 된 파일을 바이트로 가져오기
        // new FileReader() : 파일 읽기 관련 메소드 제공
    // 1. 파일 읽기 객체(메소드 사용할려고) 생성
    let file = new FileReader();
    // 2. 파일 읽기 메소드
    file.readAsDataURL(event.files[0])
    console.log(file);
    console.log(file.result);
    // 3. 파일 onload 정의
    file.onload = e => {
        console.log(e);         // progressEvent
        console.log(e.target);
        console.log(e.target.result); // 여기에 읽어온 첨부파일 바이트
        document.querySelector('#preimg').src = e.target.result;
    }
}
/*
    배열타입, 함수타입 == 객체 타입
    함수 정의 방법
    1. function 함수명(매개변수){ }
    2. function(매개변수){ }
        let 변수명 = function(매개변수){}
    3. (매개변수) =>{
        e : function(매개변수){}
    }
*/
let timer = 0; // 인증 시간
let authbox = document.querySelector('.authbox');// 1. 인증 구역
let authreqbtn = document.querySelector('.authreqbtn'); // 2. 인증요청 버튼
let timerInter = null;
//9. 인증요청
function authreq(object){
    // 2. 인증 구역 구성
    let html = `<span class = "timebox">00 : 00</span>
                <input type="text" class="ecodeinput">
                <button onclick="auth()" class="authreqbtn2" type="button" >인증</button>`
    // 3. 인증 구역 출력
    authbox.innerHTML = html;

    // === 자바에게 인증 요청 =================

    $.ajax({
            url : '/auth/email/req',
            method : 'get',
            data : {"email" : document.querySelector('#email').value},
            success : function (r){
            // 4. 결과
            if(r){
               // 4. 타이머 함수 실행
               timer = 1000;
               ontimer();
               authreqbtn.disabled = true;
            }else{
                alert('관리자에게 문의');
            }
            }
        })
};
// 10. 타이머 함수
function ontimer(){
// 테스트
    // 정의 : setInterval(함수, 밀리초) : 특정 밀리초 마다 함수 실행
    // 종료 : clearInterval(종료할Interval변수) : 종료할 Interval의 변수 대입
    // let 변수 = () => {}
    let timerInter = setInterval(()=>{
        let m = parseInt(timer / 60) ; // 분
        let s = parseInt(timer % 60) ; // 분을 제외한 초

        // 2. 시간을 두 자릿수로 표현
        m = m < 10 ? "0" + m : m; // 8분 -> 08분
        s = s < 10 ? "0" + s : s; // 3초 -> 03초
        // 3. 시간 출력
        document.querySelector('.timebox').innerHTML = `${m} : ${s}`;
        // 4. 초 감소
        timer--;
        // 5. 만약에 초가 0보다 작아지
        if(timer < 0) { //
            clearInterval(timerInter);
            authbox.innerHTML = ``; // 인증 구역 없애기
            authreqbtn.disabled = false; // 해당 버튼 사용
        }
    }, 1000);
}; // ontimer e
// 11. 인증 함수
function auth(){

    // 1. 내가 입력한 인증번호
    let ecodeinput = document.querySelector('.ecodeinput').value;
    // = 내가 입력한 인증번호를 자바에게 보내기= //
    $.ajax({
        url : '/auth/email/check',
        method : 'get',
        data : {'ecodeinput' : ecodeinput},
        success : (r) => {
        // 3. 성공시 / 실패시
        if(r){
           checkArray[4] = true // 유효성검사 통과
           document.querySelector('.emailcheckbox').innerHTML = '통과';
           clearInterval(timerInter);
           authbox.innerHTML = ``; // 인증 구역 없애기
           authreqbtn.disabled = false; // 해당 버튼 사용
        }else{
            alert('인증번호가 다릅니다.');
        }
        }
    })

}