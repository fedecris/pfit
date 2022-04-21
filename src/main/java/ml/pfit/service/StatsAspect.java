package ml.pfit.service;

import ml.pfit.dto.TraceRequestDTO;
import ml.pfit.resolve.RequestResolver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatsAspect {

    private static final Logger Log = LoggerFactory.getLogger(StatsAspect.class);

    private final StatsInterface stats;

    public StatsAspect(StatsInterface stats) {
        this.stats = stats;
    }

    @Around("execution(public ml.pfit.dto.TraceRequestDTO ml.pfit.resolve.RequestResolver.resolve(..))")
    public Object updateStats(ProceedingJoinPoint joinPoint) {
        try {
            Log.info("Tracing Started!");
            long now = System.currentTimeMillis();
            TraceRequestDTO res = (TraceRequestDTO)joinPoint.proceed();
            stats.storeRequest(res);
            long elapsed = (System.currentTimeMillis() - now);
            res.setResponseTimeMS(elapsed);
            Log.info("Tracing Finished!");
            return res;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

}
