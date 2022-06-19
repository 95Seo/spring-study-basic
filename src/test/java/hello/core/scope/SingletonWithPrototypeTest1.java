package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {
        // 싱글톤 빈으로 생성된 빈에 자동 의존 주입으로 프로토 타입 빈을 주입 받게 되면
        // 싱글톤 빈이 계속 가지고 있기 때문에
        // 프로토 타입 빈의 의미를 잃어버린다.(계속 하나의 빈만 사용된다)
//        private final PrototypeBean prototypeBean;    // 생성시점에 주입

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        // ObjectFactory는 ObjectProvider의 부모 인터페이스
        // 원래 ObjectFactory에 getObject하나 밖에 없었지만
        // ObjectProvider로 상속 구현되면서 기능이 늘어남(찾아봅시다.)
        // 단, 스프링에 종속 된다는 단점이 있다.
        // 의존관계를 외부에서 주입(DI) 받는게 아니라 이렇게 직접 필요한 의존관계를 찾는 것을 Dependency Lookup(DL) 의존관계 조회(탐색) 이라고 한다.
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

//        @Autowired
//        private ObjectFactory<PrototypeBean> prototypeBeanProvider;

        // javax provider
        // 장점
        // 1. 자바 표준(JSR330) 이기 때문에 스프링에 종속되지 않아도 된다.
        // 2. 기능이 단순하기 때문에 단위 테스트나 Mock로 만들기 편리하다.
        // 'javax.inject:javax.inject:1' 디펜던시를 추가해야 한다.
        // DL정도의(get()메서드) 단순한 기능만을 제공한다.
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init = " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
