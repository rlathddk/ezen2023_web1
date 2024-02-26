package example.day07._2스택큐컬렉션;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Example1 {
    public static void main(String[] args) {

        // 1. 스택 컬렉션 생성 [Vector 상속받음] // 후입선출
        Stack<Integer> coinBox = new Stack<>();

        // 2. 동전 넣기
        coinBox.push (100);
        coinBox.push (50);
        coinBox.push (500);
        coinBox.push (10);
        //coinBox.add(100) // add도 사용가능
        System.out.println("coinBox = " + coinBox); //coinBox = [100, 50, 500, 10]
        // 3. 동전 빼기 = pop() 스택빼기
        coinBox.pop();
        System.out.println("coinBox = " + coinBox); // 마지막 인덱스 빠짐 coinBox = [100, 50, 500]


        // 1. Queue 컬렉션 생성 // 선입선출
        Queue<String> messageQueue = new LinkedList<>();

        // 2. 메시지 넣기
        messageQueue.offer("안녕 홍길동");
        messageQueue.offer("안녕 홍길");
        messageQueue.offer("안녕 홍");
        
        messageQueue.poll();

        System.out.println("messageQueue = " + messageQueue);
    }
}
