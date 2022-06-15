package hello.core.Singleton;

public class SingletonService {

    // static - class 레벨에 올라가기 때문에 하나만 생성 됨
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance(){
        return instance;
    }

    // private 생성자를 사용해서 외부에서의 객체 생성을 막는다.
    private SingletonService() {}
    
    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
