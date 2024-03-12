package ezenweb.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString
public class MemberDto {
    int no;     /*회원번호*/
    private String id;
    private String pw;
    private String name;
    private String email;
    private String phone;
    // private String img; // 텍스트 형식 // input type = "text" (string)

    private MultipartFile img;  // type = "file (MultipartFile) 첨부파일 형식
    private String uuidFile;    // uuid+file
}
