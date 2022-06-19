package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    // request스코프의 생명주기는 http요청이 들어와서 나갈때 까지라서
    // http요청이 들어오지 않으면 빈이 없다.
    // 스프링 컨테이너가 작동되면서 의존 주입 받는 시점에 빈이 없으니 오류가 난다.
    // 프로바이더 혹은 프록시를 사용해야 한다.
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
//        MyLogger myLogger = myLoggerProvider.getObject();

        // set requestURL 부분은 인터셉터로 빼서 공통로직으로 처리 가능하다. 해보자
        // 로그인에도 사용 가능할 듯, 인터셉터로 header에 sessionId가 존재 하는지 확인하고
        // 있으면 유저 메일을 구해서 리퀘스트 파라미터로 던져준다
        // 유저 메일이 필요한 컨트롤러에 유저 메일을 보장하는 어노테이션을 만들어서 파라미터에 붙이면? -> 이거 까지는 필요 없나?
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        System.out.println("myLogger = " + myLogger.getClass());

        myLogger.log("controller test");
        logDemoService.login("id");
        return "OK";
    }
}
