package lt.dejavu.logging.aspect;

import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Order(value=2)
public class ServiceLoggingAspect {

    @Around("execution(* lt.dejavu..*.service..*.*(..))")
    public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        final Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
        Object retVal = null;

        try {
            StringBuilder startMessageStringBuffer = new StringBuilder();

            startMessageStringBuffer.append("Start method ");
            startMessageStringBuffer.append(joinPoint.getSignature().getName());
            startMessageStringBuffer.append("(");

            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                startMessageStringBuffer.append(arg).append(",");
            }
            if (args.length > 0) {
                startMessageStringBuffer.deleteCharAt(startMessageStringBuffer.length() - 1);
            }

            startMessageStringBuffer.append(")");

            logger.debug(startMessageStringBuffer.toString());

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            retVal = joinPoint.proceed();

            stopWatch.stop();

            String endMessageStringBuffer = "Finish method " +
                                            joinPoint.getSignature().getName() +
                                            "(..); execution time: " +
                                            stopWatch.getTotalTimeMillis() +
                                            " ms; return value: " +
                                            retVal;
            logger.debug(endMessageStringBuffer);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        }

        return retVal;
    }
}
