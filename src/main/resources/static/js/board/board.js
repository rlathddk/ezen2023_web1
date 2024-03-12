
// ========== 페이지 관련 객체 = 여러개 변수 묶음 =========== //

let pageObject = {      // 기본값
    page : 1,       // 현재 페이지수
    pageBoardSize : 5, // 페이지당 표시할 게시물 수
    bcno : 0,        // 현재 카테고리
    key : 'b.btitle', // 현재 검색 key
    keyword : ''        // 현재 검색 keyword
}

// 1. 전체 출력용 : 함수 : 매개변수x , 반환x, 언제 실행할건지 : 페이지 열릴때(JS)
doViewList(1); // 첫페이지 실행

function doViewList(page){
    console.log("doViewList()");
    pageObject.page = page;
    $.ajax({
        url:"/board/do",
        method:"get",
        data : pageObject,
        success : (r)=>{console.log(r)
            // 테이블에 레코드 구성 =======================
            // 1. 어디에
            let boardTableBody = document.querySelector("#boardTableBody");
            // 2. 무엇을
            let html = ``;
                // 서버가 보내준 데이터를 출력
                r.list.forEach(board => {
                    console.log(board);
                    html += `<tr>
                               <th>${board.bno}</th>
                               <td><a href= "/board/view?bno=${board.bno}">${board.btitle}</a></td>
                               <td><img src="/img/${board.mimg}" style="width:20px; border-radius:50%;" alt="">${board.mid}</td>
                               <td>${board.bdate}</td>
                               <td>${board.bview}</td>
                           </tr>`
                });
            // 3. 출력
            boardTableBody.innerHTML = html;

            // 페이지네이션 구성==================
            // 1. 어디에
            let pagination = document.querySelector('.pagination');
            // 2. 무엇을
            let pagehtml = ``;
                // 이전버튼
                pagehtml += `<li class="page-item">
                                 <a class="page-link" onclick ="doViewList(${page-1 < 1 ? 1 : page-1})">이전</a>
                             </li>`

                 // 페이지번호 버튼
                 for(let i = r.startBtn; i <= r.endBtn; i++){
                    // +만약에 i가 현재페이지와 같으면 active 클래스 삽입 아니면 생략 (*조건부 렌더링)
                    pagehtml += `<li class="page-item ${i ==page ? 'active' : ''}"><a class="page-link" onclick="doViewList(${i})">${i}</a></li>`
                 }

                // 다음버튼
                pagehtml += `<li class="page-item">
                                 <a class="page-link" onclick ="doViewList(${page+1 > r.totalPage ? r.totalPage : page+1})">다음</a>
                             </li>`

            // 3. 출력
            pagination.innerHTML = pagehtml;

           // === 3. 부가 출력 ================================================
           document.querySelector('.totalPage').innerHTML = r.totalPage;
           document.querySelector('.totalBoardSize').innerHTML = r.totalBoardSize;
        }
    });
    return;
} // end

// 2. 페이지당 게시물 수
function onPageBoardSize(object){
    console.log(object)
    console.log(object.value);
    pageObject.pageBoardSize = object.value;
    doViewList(1);
}

// 3. 카테고리 변경 함수
function onBcno(bcno){
    // bcno : 카테고리
    pageObject.bcno  = bcno;
    // 검색제거
    pageObject.key = 'b.btitle';
    pageObject.keyword = '';
    document.querySelector('.key').value = 'b.btitle'
    document.querySelector('.keyword').value = '';

    // 카테고리 활성활 css 적용
    // 1. 모든 카테고리 버튼(querySelectorAll) 호출
    let categoryBtns = document.querySelectorAll(".boardCategoryBox > button")

    // 2. 선택된 카테고리번호(매개변수bcno)에 class 대입
        // dom객체.classList.add("새로운 클래스명")
        // dom객체.classList.remove("제거할 클래스명")
    for(let i =0; i<categoryBtns.length; i++){
        categoryBtns[i].classList.remove("categoryActive");
    }
    // 2. 활성화 대입
    categoryBtns[bcno].classList.add("categoryActive")

    // 재출력
    doViewList(1);
}
// 4. 검색함수
function onSearch(){
    // 1. 입력받은 값 가져오기
    let key = document.querySelector('.key').value;
    let keyword = document.querySelector('.keyword').value;
    console.log('onSearch');
    // 2. 서버에 전송할 객체 담아주고
    pageObject.key = key;
    pageObject.keyword = keyword;
    // 3. 출력 함수 호출
    doViewList(1);



}

