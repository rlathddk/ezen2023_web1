package ezenweb.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service // 해당 클래스를 스프링 컨테이너(저장소)에 빈(객체)등록
public class FileService {
    // Controller : 중계자 역할 (HTTP 매핑, 데이터 유효성 검사)
    // Service : Controller <-- Service(비지니스로직) --> Dao

    // 어디에(PATH) 누구를(파일객체)
    String uploadPath = "C:\\Users\\504\\Downloads\\ezen2023_web1\\build\\resources\\main\\static\\img\\";

    // 1. 업로드 메소드
    public String fileUpload(MultipartFile multipartFile){
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);
        String filename = uuid+"_"+multipartFile.getOriginalFilename().replaceAll("_","-");


        File file = new File(uploadPath+filename);
        // 2. [무엇을] 첨부파일 객체
        // /.transferTo(경로)
        try {
            multipartFile.transferTo(file);
        }catch (Exception e) {
            System.out.println("e = " + e);
        }
        return filename; // 반환 : 어떤 이름으로 업로드 했는지 식별명 반환해서
    }
    // 2. 다운로드 메소드

}
