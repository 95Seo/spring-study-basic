package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// 초기화 소멸 인터페이스의 단점
// 해당 코드가 스프링 전용 인터페이스에 의존한다.
// 초기화 소멸 메서드의 이름을 변경할 수 없다.
// 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
public class NetworkClient /*implements InitializingBean, DisposableBean*/ {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + ", message = " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close : " + url);
    }

    // @PostConstruct, @PreDestroy 어노테이션 특징
    // javax패키지 하위의 어노테이션이라서 스프링이 아닌 순수 자바 환경에서도 잘 작동한다.
    // JSR-250이라는 자바 표준이다.
    // 유일한 단점 : 외부 라이브러리에는 적용하지 못한다. 외부 라이브러리를 초기화, 종료 해야하면 @Bean의 initMethod, destroyMethod 를 사용하자.
    @PostConstruct
    public void init() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }

//    // InitializingBean 상속
//    // 의존 관계 주입이 끝나면(초기화 콜백) 실핼
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    // DisposableBean 상속
//    // 빈이 종료될때(소멸 콜백) 실행
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }
}
