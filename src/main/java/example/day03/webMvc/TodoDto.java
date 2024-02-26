package example.day03.webMvc;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class TodoDto {
    // 1. 필드 (dto로 사용할 경우 db table 필드와 일치하고 추가적으로 추가기능)
    private int id;
    private String content;
    private String deadline;
    private boolean state;

}
