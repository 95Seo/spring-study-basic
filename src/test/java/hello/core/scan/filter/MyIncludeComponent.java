package hello.core.scan.filter;

import java.lang.annotation.*;

// 어노테이션 만드는건 다시 공부해야한다...
// 어렵다...
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {

}
