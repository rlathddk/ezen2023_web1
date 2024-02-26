package example.day06._1MAP컬렉션;

import java.util.*;

public class Example1 {
    public static void main(String[] args) {

        /*
            Map 컬렉션
                - key와 value 구성된 엔트리(객체)를 저장
                - key는 중복 불가능
                - MAP인터페이스
                    - 구현클래스 :
                            HashMap : 동기화x
                            HashTable : 동기화o, 멀티스레드 권장

                    - 선언방법
                        Map<K, v> map = new HashMap<>()
                    - 사용방법/메소드
                        .put(key, value)    : 엔트리 추가
                        .get(key)           : 주어진 key 이용한 value 호출
                        .remove(key)        : 주어진 key의 해당하는 엔트리 삭제
                        .keySet()           : 전체 키를 set컬렉션 반환
                        .entrySet()         : 전체 엔트리 반환
                        .values()           : 전체 값을 컬렉션 반환
                        .clear()            : 전체 엔트리 삭제
                        .size()             : 전체 엔트리 개수 반환
        */


        // 1. MAP 컬렉션 생성
        Map<String, Integer> map = new HashMap<>();
        // Map<String, Integer> map = new HashMap<>();  // 재네릭 타입은 기본타입 불가능

        // 2. 컬렉션 객체에 객체 (엔트리 = key, value ) 넣기
        map.put("신용권", 85);
        map.put("홍길동", 90);
        map.put("동장군", 80);
        map.put("홍길동", 95);
        System.out.println("map = " + map);

        // 3. 키로 값 얻기
        System.out.println(map.get("홍길동")); // 95

        // - 순회 : 인덱스없음
        // keySet() : 모든 키를 set컬렉션을 반환
        // entrySet() : 모든 엔트리를 set컬렉션을 반환
        // .value() : 모든 값을 컬렉션으로 반환

        // 1. 간편..
        Set<String> keySet = map.keySet();
        for(String key : map.keySet()){
            System.out.println("key = " + key);
            System.out.println("value =" + map.get(key));
        }
        // 2. 복잡..
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        System.out.println("ddd" + entrySet);
        Iterator<Map.Entry<String, Integer>> entryIterator = entrySet.iterator();
        while (entryIterator.hasNext()){
            Map.Entry<String, Integer> entry = entryIterator.next();
            System.out.println(entry);
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        // 3.
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println(entry);
        }
        map.keySet().forEach(key-> System.out.println("key = " + key)); // forEach() 함수는 배열의 각 요소를 순회하며 주어진 함수를 호출

        // 클래스명.getInstance().login() //

        // 4.
        for(Integer value : map.values()) System.out.println("value = " + value);

        // 5. 총 엔트리 개수
        System.out.println("총 엔트리 개수 : " + map.size());

        // 4. 키로 엔트리 삭제
        map.remove("홍길동");
        System.out.println("map = " + map);     // key 넣어서 엔트리(key,value) 삭제

        // p.663 ==================================================================== //

        // 1. properties 객체  // 이렇게 쓰는 이유 ? 은닉화
            // - String 타입
        Properties properties = new Properties();
        // 2.
        properties.setProperty("driver", "com.mysql.jdbc.cj.Driver");
        properties.setProperty("URL","jdbc:mysql://localhost:3306/springweb");
        properties.setProperty("admin","root");
        properties.setProperty("password","1234");
        // 3.
        System.out.println(properties.getProperty("driver"));
        System.out.println(properties.getProperty("URL"));
        System.out.println(properties.getProperty("admin"));
        System.out.println(properties.getProperty("password"));
    }
}
