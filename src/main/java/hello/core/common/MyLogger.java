package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

// @Scope(value = "quest")을 이용해서 request스코프로 생성했다.
// http 요청 당 하나씩 생성되고 요청이 끝날때 소멸된다.
// requestURL 은 빈이 생성되는 시점에는 알수 없어서 setter로 받아옴
// proxyMode = ScopedProxyMode.TARGET_CLASS CGLIB라이브러리가 내 클래스를 상속받는 가짜 프록시 클래스를 만들어서 주입시켜 준다.
// 사용될때 진짜 객체를 찾아 대체한다.
// 타겟이 클래스면 TARGET_CLASS 인터페이스면 INTERFACES
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }
}
