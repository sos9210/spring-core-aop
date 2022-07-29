package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * application.properties
 * spring.aop.proxy-target-class=true  CGLIB
 * spring.aop.proxy-target-class=false JDK동적프록시
 */
@Slf4j
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    //this 프록시객체
    //target target이 가르키는 실제객체
    @Slf4j
    @Aspect
    static class ThisTargetAspect {
        //부모타입허용
        @Around("this(hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //jdk동적프록시로 만들어진 proxy객체는 MemberService인터페이스 기반으로 구현된 새로운 클래스다.
        //따라서 MemberServiceImpl을 전혀 모르므로 호출되지않는다.
        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-Impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //부모타입허용
        @Around("target(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-Impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
