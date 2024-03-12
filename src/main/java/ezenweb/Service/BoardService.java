package ezenweb.Service;

import ezenweb.model.dao.BoardDao;
import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.BoardPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BoardService {
    @Autowired
    private BoardDao boardDao;
    @Autowired
    private FileService fileService;
    // 1. 글쓰기 처리
    public long doPostBoardWrite(BoardDto boardDto){
        System.out.println("BoardController.doPostBoardWrite");

        // 1. 첨부 파일 처리
            // 첨부파일이 존재하면
        if(!boardDto.getUploadfile().isEmpty()){
            String fileName = fileService.fileUpload((boardDto.getUploadfile()));
            if(fileName != null){
                boardDto.setBfile(fileName); // db 저장할 첨부파일명 대입
            }else {return -1;}
        }
        return boardDao.doPostBoardWrite(boardDto);
    }

    // 2. 전체 글 출력 호출
    public BoardPageDto doGetBoardViewList(int page, int pageBoardSize, int bcno, String field, String value ){
        System.out.println("BoardController.doGetBoardView");
        // 1. 페이지당 게시물을 출력할 개수
        // int pageBoardSize = 2;

        // 2. 페이지당 게시물을 출력할 시작 레코드 번호 [시작레코드번호(0부터)]
        int startRow = (page-1) * pageBoardSize;

        // 3. 총 페이지수
            // 1. 전체 게시물수
        int totalBoardSize = boardDao.getBoardSize(bcno, field, value);
            // 2. 총 페이지 계산
        int totalpage = totalBoardSize % pageBoardSize == 0 ?
                        totalBoardSize / pageBoardSize :
                        totalBoardSize / pageBoardSize +1;
        System.out.println("totalpage = " + totalpage);

        // 4. 게시물 정보 요청
        List<BoardDto> list = boardDao.doGetBoardViewList(startRow, pageBoardSize, bcno, field, value);

        // 5. 페이징 버튼 개수
            // 1. 페이지버튼 최대 개수
        int btnSize = 5;        // 5개씩
            // 2. 페이지버튼 시작번호
        int startBtn = ((page-1)/btnSize*btnSize)+1;       // 1번 버튼
            // 3. 페이지버튼 끝번호
        int endBtn = startBtn+btnSize-1;         // 5번 버튼
            // 만약에 총페이지수 보다는 커실 수 없다
        if(endBtn >= totalpage) endBtn = totalpage;

        // pageDto 구성 * 빌더패턴 : 생성자의 단점(매개변수에 따른 유연성 부족)을 보완
            // new 연산자 없이 builder() 함수 이용한 객체 생성 라이브러리 제공
            // 사용방법 : 클래스명.builder().필드명(대입값).필드명(대입값).build();
            // + 생성자 보단 유연성 : 매배견수의 순서와 개수 자유롭다
                // 빌터패턴 vs 생성자 vs setter
        BoardPageDto boardPageDto = BoardPageDto.builder()
                .page(page)
                .totalBoardSize(totalBoardSize)
                .totalPage(totalpage)
                .list(list)
                .startBtn(startBtn)
                .endBtn(endBtn)
                .build();
        return boardPageDto;
    }

    // 3. 개별 글 출력 호출
    public BoardDto doGetBoardView(@RequestParam int bno){
        System.out.println("BoardController.doPostBoardView");
        // 조회수 처리 // 조회수 많으면 수익 : 조회수 처리 했다/안했다 증거 남겨서 하루에 한번 또는 회원마다 한번 (log, 세션),
        boardDao.boardViewIncrease(bno);

        // 게시물 출력 요청
        return boardDao.doGetBoardView(bno);
    }

    // 4. 글 수정 처리(매개변수 : bno, btitle, bcontent, uploadfile, bcno)
    public boolean doUpdateBoard(BoardDto boardDto) {
        // 1. 기존 첨부파일명 구하고
        String bfile = boardDao.doGetBoardView((int)boardDto.getBno()).getBfile();
        // - 새로운 첨부파일이 있다. 없다
         if(!boardDto.getUploadfile().isEmpty()){ // 수정시 새로운 첨부파일이 있으면
             // 새로운 첨부파일을 업로드하고 기존 첨부파일 삭제
             String fileName = fileService.fileUpload(boardDto.getUploadfile());
             if(fileName !=null){ // 업로드 성공
                 boardDto.setBfile(fileName); // 새로운 첨부파일의 이름 dto 대입
                 // 기존 첨부파일 삭제
                    // 2. 기존 첨부파일 삭제
                 fileService.fileDelete(bfile);
             }else {
                 return false; // 업로드 실패
             }
         }else{
             boardDto.setBfile(bfile);
             // 업로드 할 필요 없다
             // 기존 첨부파일명을 그대로 대입
         }
        return boardDao.doUpdateBoard(boardDto);
    }

    // 5. 글 삭제 처리
    public boolean doDeleteBoard(  int bno ){   System.out.println("BoardService.doDeleteBoard");
        // - 레코드 삭제 하기전에 삭제할 첨부파일명을 임시로 꺼내둔다.
        String bfile = boardDao.doGetBoardView( bno ).getBfile();
        // 1. DAO 처리
        boolean result = boardDao.doDeleteBoard(bno);
        // 2. Dao 처리 성공시 첨부파일도 삭제
        if( result ){
            if( bfile != null ){ // 기존에 첨부파일이 있었으면.
                fileService.fileDelete( bfile ); // 미리 꺼내둔 삭제할 파일명 대입한다.
            }
        }
        return result;
    }

    // 6. 게시물 작성자 인증
    public boolean boardWriterAuth(long bno, String mid){
        return  boardDao.boardWriterAuth(bno,mid);
    }

    // 7. 댓글 등록 (brcontent, brindex, , mno, bno)
    public boolean postReplyWrite(@RequestParam Map<String, String> map){
        System.out.println("map = " + map);
        return boardDao.postReplyWrite(map);
    }

    // 8. 댓글 출력
    public List<Map<String, Object>> getReplyDo(int bno){

        System.out.println("BoardService.getReplyDo");
        return boardDao.getReplyDo(bno);
    }
} // end
