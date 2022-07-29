package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    //hello.aop.order 패키지와 하위 패키지이면서 클래스이름 패턴이 *Service
/*    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
        try{
            //@Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            //@AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        }catch (Exception e) {
            //@AfterThrowing
            log.info("[트랜잭션 롤백] {}",joinPoint.getSignature());
            throw e;
        }finally {
            //@After
            log.info("[리소스 릴리즈] {}",joinPoint.getSignature());
        }
    }*/
    //joinPoint.proceed() 실행전단계 까지.
    //자동으로 다음 target이 호출된다.
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}",joinPoint.getSignature());
    }

    //리턴타입이 일치하지않으면 호출되지않는다.
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}",joinPoint.getSignature(),result);
    }

    //자동으로 예외를 던진다
    //예외타입이 일치하지않으면 호출되지않는다.
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doReturn(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}",joinPoint.getSignature(),ex);
    }

    @After("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}",joinPoint.getSignature());
    }
}
