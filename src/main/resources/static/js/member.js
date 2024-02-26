// 1. 회원가입
function signup(){
    console.log('signup()')
    // 1. HTML 입력값 호출 [document.querySelector()]
    let id = document.querySelector('#id').value; //#id 또는 .class 또는 [name]
    let pw = document.querySelector('#pw').value;
    let name = document.querySelector('#name').value;
    let phone = document.querySelector('#phone').value;
    let email = document.querySelector('#email').value;
    let img = document.querySelector('#img').value;
    // 2. 객체화 [ let info = {} ]
    let info = {
        id : id, pw : pw, name : name, phone : phone, email : email, img : img
    }; console.log(info);
    // 3. controller 통신(AJAX) -> 원래는 객체를 배열에 저장했었음
    $.ajax({
            url : '/member/signup' ,
            method : 'POST' ,
            data : info,
            success : function (result){
            // 4. 결과
            if(result){
                alert('회원가입 성공');
                location.href = '/member/login'
            }else{
                alert('회원가입 실패');
            }
                console.log(result);
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