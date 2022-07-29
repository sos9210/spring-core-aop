package hello.aop.exam;

import hello.aop.exam.annotation.Retry;
import hello.aop.exam.annotation.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ExamRepository {
    private static int seq = 0;

    /**
     * 5번에 1번 실패하는 요청
     */
    @Trace
    @Retry(4)   //default = 3 이지만 지정해서 써봤다.
    public String save(String itemId) {
        seq++;
        log.info("seq = {}", seq);
        if (seq % 5 == 0){
            throw new IllegalStateException("예외 발생");
        }

        return "ok";
    }
}
