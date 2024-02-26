package example.day11._2Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/day11")
public class RestController3 {// HttpServletResponse resp ㅇ벗애기

    // 1. Get
    //@RequestMapping(value = "/day11/white", method = RequestMethod.GET)

    @GetMapping (value = "/red")// 응답 contentType : application/json
    public String getRed(HttpServletRequest req)throws IOException {
        System.out.println("RestController1.getBlack");
        // 요청
        String sendMsg = req.getParameter("sendMsg");
        System.out.println("sendMsg = " + sendMsg);
        // 응답
        return "안녕[클라이언트]";
    } // end
    // 2. Post
    @PostMapping("/red")
    public Map<String, String> postRed (HttpServletRequest req) throws IOException {
        System.out.println("RestController1.postBlack");
        // 요청
        String sendMsg = req.getParameter("sendMsg");
        System.out.println("sendMsg = " + sendMsg);
        // 응답
//       String[] strArray = new String[2];
//       strArray[0]= "안녕";strArray[1] = "클라이언트";

        Map<String , String> strArray = new HashMap<>();
        strArray.put("안녕","클라이언트");
       return strArray;

    }
    // 3. Put
    @PutMapping("/red")
    public int putRed (HttpServletRequest req) throws IOException {
        // 요청
        String sendMsg = req.getParameter("sendMsg");
        System.out.println("sendMsg = " + sendMsg);
        // 응답
        return 10;
    }
    // 4. delete
    @DeleteMapping("/red")
    public boolean deleteRed (HttpServletRequest req) throws IOException {
        // 요청
        String sendMsg = req.getParameter("sendMsg");
        System.out.println("sendMsg = " + sendMsg);
        // 응답
        return true;
    }
}
