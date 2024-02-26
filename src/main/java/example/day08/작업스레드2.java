package example.day08;

import java.awt.*;

public class 작업스레드2 extends Thread{ // extends말고 Runnable를 쓰는 이유 : extends는 한번 밖에 안되기 떄문에 추가로 extends 필요시 사용불가  
    // 작업스레드가 실행하는 함수 정의
    @Override
    public void run(){
       for(int i = 1; i<=5; i++){
           Toolkit toolkit = Toolkit.getDefaultToolkit();
           toolkit.beep();
           try{Thread.sleep(500);}catch (InterruptedException e){};
       }
    }
}// c e
