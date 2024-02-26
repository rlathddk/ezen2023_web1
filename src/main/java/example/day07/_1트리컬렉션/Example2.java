package example.day07._1트리컬렉션;

import java.util.Map;
import java.util.TreeMap;

public class Example2 {
    public static void main(String[] args) {
        // 1. TreeMap 컬렉션 생성
        TreeMap<String,Integer> treeMap = new TreeMap<>();

        // 2. TreeMap 컬렉션/객체에 여러개 엔트리/객체 저장
        treeMap.put("apple", 100);
        treeMap.put("forever", 60);
        treeMap.put("description", 40);
        treeMap.put("ever", 50);
        treeMap.put("zoo", 80);
        treeMap.put("base", 20);
        treeMap.put("guess", 70);
        treeMap.put("cherry", 30);
        System.out.println("treeMap = " + treeMap);

        // 3. 순회
        for(Map.Entry<String, Integer> entry : treeMap.entrySet()){
            System.out.println("entry = " + entry);
        }System.out.println();
        treeMap.entrySet().forEach(entry-> System.out.println("entry = " + entry));
        System.out.println();

        // 4. 일반 map컬렉션보다 추가적인 메소드 제공
        System.out.println("제일 앞 단어 : " + treeMap.firstEntry());
        System.out.println("제일 뒤 단어 : " + treeMap.lastEntry());
        System.out.println("ever 앞 단어 : " + treeMap.lowerEntry("ever"));

        System.out.println(treeMap.descendingMap()); // 키, 값 중 키가 기준점임 값이 100으로 바뀌어도 apple이 출력

        // 5. 범위 검색
        System.out.println(treeMap.subMap("c", true,"h",false)); // c는 포함하고 h는 포함하지 않는
    }
}
