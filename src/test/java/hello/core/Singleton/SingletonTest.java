package hello.core.Singleton;

import hello.core.AppConfig;
import hello.core.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

// 웹 어플리케이션의 특징 : 요청이 많다
// 요청마다 객체를 생성하면 부담이 된다.

// 싱글톤 패턴의 문제점
// 구현하는 코드 자체가 많이 들어간다.
// 의존관계상 클라이언트가 구체 클래스에 의존한다. -> DIP를 위반
// 테스트 하기 어렵다.
// 내부 속성을 변경하거나 초기화 하기 어렵다.
// private 생성자로 자식 클래스를 만들기 어렵다.
public class SingletonTest {

    // 순수한 DI 컨테이너는 요청마다 객체를 생성함
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // same ==
        // equal object.equals()
        assertThat(singletonService1).isSameAs(singletonService2);
    }
    
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

//        AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        assertThat(memberService1).isSameAs(memberService2);
    }
}
