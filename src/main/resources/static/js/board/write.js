
// 썸머노트 실행
$(document).ready(function() {
  // 썸머노트 옵션
  let option = {
    lang : 'ko-KR', // 한글패치
    height : 500, // 에디터 세로 크기
  }
  $('#summernote').summernote(option);

});

// 1. 글쓰기

function onWrite(){
    console.log("onWrite()");

    // 1. 폼DOM 가져온다
    let boardWriteForm = document.querySelector('.boardWriteForm');
    console.log(boardWriteForm)
    // 2. 폼 바이트(바이너리) 객체 변환[첨부파일 보낼때는 필수]
    let boardWriteFormData = new FormData(boardWriteForm);

    // 3. ajax 첨부파일 폼 전송
    $.ajax({
        url : "/board/write.do",
        method: "post",
        data : boardWriteFormData,
        contentType : false, //
        processData : false, // multipart/form-data로 이미지를 서버로 전송하려고 할 때  500 에러가 발생하면서 해결법을 찾을 때 나온 속성들 (contentType, processData)

        success : (r)=> {
            if(r==0){
                alert('글쓰기 실패 : DB오류');
                location.href="/board/view?bno=1"
            }else if(r == -1){
                alert('글쓰기 실패 : 관리자에게문의(첨부파일 오류)')
            }else if(r == -2){
                 alert('글쓰기 실패 : 로그인 세션이 존재하지 않음')
            }
            else if(r >=1){
                 alert('글쓰기 성공')
                 location.href = '/board/view?bno='+r;
            }
        }
    }) // ajax end
}