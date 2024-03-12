package ezenweb.model.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {
    private int pno;
    private String pname;
    private int pprice;
    private String pcontent;
    private byte pstate;
    private String pdate;
    private String plat;
    private String plng;
    private int mno;

    // - 등록할 때 이미지
    private List<MultipartFile> uploadFiles;

    // - 출력할 때 이미지
    private List<String> pimg;

    // - 출력 시 작성자 번호가 아닌 작성자 아이디
    private String mid;


    // + 1. 제픔 등록 [pname pprice pcontent plat plng mno(세션)]

    // + 2. 제품 지도 출력 [pno pname pprice pstate plat plng]

    // + 3. 제품 출력 [pno pname pprice pcontent pstate plat plng ]
}
