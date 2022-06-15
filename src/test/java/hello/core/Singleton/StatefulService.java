package hello.core.Singleton;

// ***중요***
// 공유 필드는 정말 조심해야 한다!!
// 스프링 빈은 항상 무상태(stateless)로 설계하자.
public class StatefulService {

//    private int price;  // 상태를 유지하는 필드

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price; // 여기가 문제!
    }

//    public int getPrice() {
//        return price;
//    }
}
