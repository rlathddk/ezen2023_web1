// 1. 회원가입
function signup(){
    console.log('signup()')
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
    // 4. 결과

// 2. 로그인
function login(){
    console.log("login()");
    // 1. HTML 입력값 호출
    let id = document.querySelector('#id').value; console.log(id);
    let pw = document.querySelector('#pw').value; console.log(pw);
    // 2. 객체화
    let info = {id : id, pw : pw, };
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