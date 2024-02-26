package example.day11._2Controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/day11") // 해당 클래스내 매핑함수들의 공통URL
public class RestController4 {

    @GetMapping("/ajax1")
    public String ajax1(){
        System.out.println("RestController4.ajax1");
        return "응답1";
    }
    // 2. 경로상 변수 활용한 매개변수 요청
    @GetMapping("/ajax2/{id}/{content}")
    public String ajax2(@PathVariable int id, @PathVariable String content){
        System.out.println("RestController4.ajax2");
        return "응답2";
    }
    // 3. 쿼리스트링
// 4. DTO
    @GetMapping("/ajax3")
    public String ajax3(AjaxDto ajaxDto){
        System.out.println("RestController4.ajax3");
        System.out.println("ajaxDto = " + ajaxDto);
        return "응답3";
    }

    // 4. HTTP BODY 본문
//    @GetMapping("/ajax4")
//    public String ajax4(int id, @RequestParam("content") String content){
//        System.out.println("RestController4.ajax4");
//        System.out.println("id = " + id + ", content = " + content);
//        return "응답4";
//    }
    @GetMapping("/ajax4")
    public String ajax4(Map<String,String>map){
        System.out.println("RestController4.ajax4");
        System.out.println("map = " + map);
        return "응답4";
    }
// 3. @RequestParam
//    @GetMapping("/ajax3")
//    public String ajax3(@RequestParam Map<String, String> map){
//        System.out.println("RestController4.ajax3");
//        System.out.println("map = " + map);
//        return "응답3";
//    }
// 1. @RequestParam
//    @GetMapping("/ajax3")
//    public String ajax3(int id,@RequestParam("content") String content2){
//        System.out.println("RestController4.ajax3");
//        System.out.println("id = " + id + ", content = " + content2);
//        return "응답3";
//    }
} // c e
