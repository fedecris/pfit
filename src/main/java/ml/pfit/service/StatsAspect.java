package ml.pfit.service;

import ml.pfit.dto.TraceRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatsAspect {

    private final StatsInterface stats;

    @Autowired
    public StatsAspect(StatsInterface stats) {
        this.stats = stats;
    }

    @Around("execution(public ml.pfit.dto.TraceRequest ml.pfit.resolve.RequestResolver.resolve(..))")
    public Object updateStats(ProceedingJoinPoint joinPoint) {
        try {
            long now = System.currentTimeMillis();
            TraceRequest res = (TraceRequest)joinPoint.proceed();
            stats.storeRequest(res);
            long elapsed = (System.currentTimeMillis() - now);
            res.setResponseTimeMS(elapsed);
            return res;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

}
