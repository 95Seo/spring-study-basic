package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// *참고 : 객체의 생성과 초기화를 분리하자.
// 스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원한다.
// 1. 인터페이스(InitializingBean, DisposableBean)
// 2. 설정 정보에 초기화 메서드, 종료 메서드 지정
// 3. @PostConstruct, @PreDestory 어노테이션 지원

// 기본적인(싱글톤 컨텍스트) 스프링 빈 라이프 사이클
// 스프링 컨테이너 생성 -> 스프링 빈(객체) 생성(생성자 주입의 경우 이 시점에 의존관계 주입이 일어남) -> 의존관계 주입 -> 초기화 콜백(사용 준비가 되었다) -> 사용 -> 소멸 전 콜백 -> 스프링 종료
// 초기화 콜백 : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
// 소멸 전 콜백 : 빈이 소멸되기 직전에 호출
public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        // close는 기본 ApplicationContext에서는 제공해 주지 않는다.
        // 그렇기 때문에 ApplicationContext를 상속받는 ConfigurableApplicationContext나 AnnotationConfigApplicationContext로 생성해야 한다.
        // 상속관계 부모 -> 자식 순서 ApplicationContext -> ConfigurableApplicationContext -> (AbstractApplicationContext -> GenericApplicationContext) -> AnnotationConfigApplicationContext
        // AnnotationConfigApplicationContext는 ConfigurableApplicationContext의 자식 인터페이스이기 때문에 ConfigurableApplicationContext타입으로 받는것이 가능
        ac.close();     // 컨테이너 종료
    }

    @Configuration
    static class LifeCycleConfig {

        // 초기화 소멸 메서드 특징
        // 메서드 이름이 자유롭다.
        // 스프링 빈이 스프링 코드에 의존하지 않는다.
        // 코드가 아니라 설정 정보를 사용하기 떄문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
        // @Bean 의 destroyMethod 메서드는 기본값이 (inferred) (추론)으로 등록되어 있다.
        // 이 추론 기능은 close, shutdown라는 이름의 메서드를 자동으로 호출해준다.
        // 따라서 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 작동한다.
        // 사용하기 싫으면 destroyMethod = "" (공백)으로 처리하면 된다.
//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
