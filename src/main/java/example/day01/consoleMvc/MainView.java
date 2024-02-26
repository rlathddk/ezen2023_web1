package example.day01.consoleMvc;


import java.util.ArrayList;
import java.util.Scanner;

public class MainView {
    Scanner scanner= new Scanner(System.in);
    private TodoController todoController = new TodoController();

    public void home(){
        while (true){
            doGet();
            System.out.println("1. 할 일 등록 : ");
            int ch = scanner.nextInt();
            if(ch==1){doPost();} // 할일 목록 출력
        }
    } // m e

    // 2. 할일 등록 함수
    public void doPost(){
        // 1. 입력받기
        System.out.println("할 일 내용 : "); String content = scanner.next();
        System.out.println("마입일[yyyy-mm-dd] : "); String deadLine = scanner.next();

        // 2. 객체
        TodoDto todoDto = new TodoDto();
        todoDto.setContent(content);
        todoDto.setDeadLine(deadLine);

        // 3. 컨트롤에게 보내기
        boolean result = todoController.doPost(todoDto);

        // 4. 응답결과 출력하기
        System.out.println(result);
    } // m e

    // 3. 할일 목록 출력 함수
    public void doGet(){
        // 1. 입력받기 - 전체 출력이라서 조건이 없음
        // 2. 객체화도 할 것 없음
        // 3. 컨틑롤에게 요청 응답 받기
        ArrayList<TodoDto> result = todoController.doGet();

        // 4. 응답결과 출력하기
        for(int i = 0 ; i<result.size(); i++){
            // i번째 dto를 호출
            TodoDto todoDto = result.get(i);
            System.out.printf("%2s %10s %10s %10s \n" ,
                    todoDto.getId(),
                    todoDto.getDeadLine(),
                    todoDto.isState(),
                    todoDto.getContent()
                    );
        }
    }// m e
} // c e
