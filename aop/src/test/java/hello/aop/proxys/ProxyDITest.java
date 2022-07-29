package hello.aop.proxys;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxys.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
@Slf4j
//@SpringBootTest 는 기본적으로 "spring.aop.proxy-target-class=true" 상태임
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) //JDK 동적 프록시
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    //JDK동적프록시는 인터페이스 기반으로 만들어진다
    @Autowired
    MemberService memberService;

    //스프링 빈으로 등록된 프록시가 주입되는데 프록시는 MemberService의 구현 클래스에대해 전혀 모른다.
    @Autowired
    MemberServiceImpl memberServiceImpl;    //에러!!

    //CGLIB는 구체클래스를 상속받아서 생성하기때문에 성공
    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberService.hello("aaa");
    }

}
