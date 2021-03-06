package lt.akademija.jpaexam.grader;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Grader {

    private static AtomicInteger totalScore = new AtomicInteger(0);
    private static AtomicInteger score = new AtomicInteger(0);

    public synchronized static void graded(Integer value, GradedBody body) {
        totalScore.addAndGet(value);
        body.graded();
        score.addAndGet(value); //If not exception then tests passes
    }

    public synchronized static Grade getCurrentGrade() {
        int total = totalScore.get();
        int score = Grader.score.get();
        BigDecimal averageGrade = new BigDecimal(score)
                .divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.TEN);
        return new Grade(
                total,
                score,
                averageGrade,
                averageGrade.setScale(0, BigDecimal.ROUND_HALF_UP).intValue()
        );
    }

    public interface GradedBody {
        void graded();
    }
}
