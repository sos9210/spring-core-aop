package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {
    //***문제점***
    //내부 메서드 호출.. 대상객체에서 내부메서드를 호출하기때문에 스프링AOP가 적용되지않음 (프록시 방식 AOP의 한계)

    //해결방법
    //1.AspectJ를 직접 사용한다. (프록시 방식이 아닌, 바이트코드 조작을통해 AOP코드가 실제 메서드마다 직접들어간다) 하지만 AspectJ는 거의 사용하지않는다.

    //2.세터주입
    //2-1.내부호출이 아닌 자기자신(대상객체)을 세터 주입을 받고 외부메서드 호출 ( 스프링 2.6부터 세터주입으로 순환참조 금지..

    //3.스프링컨테이너 직접조회
    //3-1.ApplicationContext에서 직접 스프링 빈을 조회해서 외부메서드 호출

    //4.지연참조
    //4-1.ObjectProvider에서 getObject()메서드 사용(호출시점에 스프링컨테이너에서 스프링빈 조회)

    //5.내부호출금지 (가장좋은방법이며 스프링에서 권장하는방법, 상황에따라 다양한방법)
    //ex)클래스 별도 분리

    //1,2,3,4 방법이 금지되는건 아니지만 가급적 5번을 권장한다.
    public void external() {
        log.info("call external");
        internal(); //내부 메서드 호출(this.internal())
    }

    public void internal() {
        log.info("call internal");
    }
}
