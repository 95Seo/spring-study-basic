package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // basePackages 부터 컴포넌트 탐색을 시작한다 - member 하위의 @Component만 탐색
        basePackages = "hello.core.member",
        // basePackageClasses로 지정한 class 의 패키지 하위의 class 들을 탐색
        basePackageClasses = AutoAppConfig.class,
        // 필터를 거쳐서 Configuration 어노테이션이 붙은 클래스는 컴포넌트 스캔에서 제외한다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // 지정하지 않으면 현재(@ComponentScan) 이 붙은 class의 패키지 하위의 class 들을 탐색
)
public class AutoAppConfig {
}
